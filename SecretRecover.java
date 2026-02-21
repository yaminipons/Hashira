import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class Point {
    BigInteger x;
    BigInteger y;

    Point(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }
}

public class SecretRecover {

    static BigInteger decode(String value, int base) {
        return new BigInteger(value, base);
    }

    static BigInteger findSecret(List<Point> points, int k, BigInteger prime) {

        BigInteger secret = BigInteger.ZERO;

        for (int i = 0; i < k; i++) {

            BigInteger xi = points.get(i).x;
            BigInteger yi = points.get(i).y;

            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;

            for (int j = 0; j < k; j++) {

                if (i == j) continue;

                BigInteger xj = points.get(j).x;

                numerator = numerator.multiply(xj.negate()).mod(prime);
                denominator = denominator.multiply(xi.subtract(xj)).mod(prime);
            }

            BigInteger lagrange =
                    numerator.multiply(denominator.modInverse(prime)).mod(prime);

            secret = secret.add(yi.multiply(lagrange)).mod(prime);
        }

        return secret.mod(prime);
    }

    public static void main(String[] args) throws Exception {

        String content = Files.readString(Paths.get("input.json"));
        JSONObject root = new JSONObject(content);

        JSONObject keys = root.getJSONObject("keys");
        int k = keys.getInt("k");

        List<Point> points = new ArrayList<>();

        for (String key : root.keySet()) {

            if (key.equals("keys")) continue;

            JSONObject obj = root.getJSONObject(key);

            int base = Integer.parseInt(obj.getString("base"));
            String value = obj.getString("value");

            BigInteger x = new BigInteger(key);
            BigInteger y = decode(value, base);

            points.add(new Point(x, y));
        }

        points.sort(Comparator.comparing(p -> p.x));
        List<Point> required = points.subList(0, k);

        BigInteger prime = new BigInteger(
                "208351617316091241234326746312124448251235562226470491514186331217050270460481"
        );

        BigInteger secret = findSecret(required, k, prime);

        System.out.println("SECRET = " + secret);
    }
}