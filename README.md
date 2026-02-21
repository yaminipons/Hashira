# Shamir Secret Sharing – Secret Recovery

## Candidate

Name: <Your Name>
Language Used: Java

---

## Problem Statement

Given multiple roots (shares) of a polynomial in JSON format, recover the constant term of the polynomial (the secret) using **k minimum shares**.

The shares are encoded in different number bases (binary, decimal, hex, etc.), so they must first be converted to decimal before computation.

---

## Approach

1. Read input from `input.json`
2. Parse JSON data
3. Convert each share to (x, y) point
4. Select first k points
5. Apply **Lagrange Interpolation at x = 0**
6. Compute secret using modular arithmetic (BigInteger)

The recovered constant term = **Secret**

---

## How to Run

### Compile

javac -cp ".;json.jar" SecretRecover.java

### Run

java -cp ".;json.jar" SecretRecover

---

## Files Included

* `SecretRecover.java` → Main solution
* `input.json` → Sample input
* `json.jar` → JSON parser library

---

## Output

The program prints:

SECRET = <recovered_secret>

---

## Notes

* Works for very large numbers using BigInteger
* Supports any base encoding provided in input
* Uses only k minimum shares as required
* Implements Shamir Secret Sharing reconstruction
