package fr.inria.diverse.mobileprivacyprofilerserver.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

public class AuthenticationUtil {

    public static final AuthenticationUtil INSTANCE = new AuthenticationUtil();
    public static final int Length = 10;
    static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final Random RANDOM = new SecureRandom();
    static final int ITERATIONS = 10000;
    static final int KEY_LENGTH = 512;

    private AuthenticationUtil(){}


    /**
     * Returns a random salt to be used to hash a password.
     *
     * @return a 20 bytes random salt
     */
    public byte[] generateSalt() {
        byte bytes[] = new byte[20];
        RANDOM.nextBytes(bytes);
        return bytes;
    }

    /**
     * Returns a salted and hashed password using the provided hash.<br>
     * Note - side effect: the password is destroyed (the char[] is filled with zeros)
     *
     * @param password the password to be hashed
     * @param salt     a 16 bytes salt, ideally obtained with the getNextSalt method
     *
     * @return the hashed password with a pinch of salt
     */
    public byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    /**
     * Returns true if the given password and salt match the hashed value, false otherwise.<br>
     * Note - side effect: the password is destroyed (the char[] is filled with zeros)
     *
     * @param password     the password to check
     * @param salt         the salt used to hash the password
     * @param expectedHash the expected hashed value of the password
     *
     * @return true if the given password and salt match the hashed value, false otherwise
     */
    public boolean isExpectedPassword(char[] password, byte[] salt, byte[] expectedHash) {
        byte[] pwdHash = hash(password, salt);
        Arrays.fill(password, Character.MIN_VALUE);
        if (pwdHash.length != expectedHash.length) return false;
        for (int i = 0; i < pwdHash.length; i++) {
            if (pwdHash[i] != expectedHash[i]) return false;
        }
        return true;
    }

    /**
     * Generates a random password of a given length, using letters and digits.
     *
     * @param length the length of the password
     *
     * @return a random password
     */
    public String generateRandomPassword(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int c = RANDOM.nextInt(62);
            if (c <= 9) {
                sb.append(String.valueOf(c));
            } else if (c < 36) {
                sb.append((char) ('a' + c - 10));
            } else {
                sb.append((char) ('A' + c - 36));
            }
        }
        return sb.toString();
    }

    /**
     * Create a token using the username
     * @param username
     * @return a token
     */
    public String createToken(String username){

        Key key = MacProvider.generateKey();

        return  Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}
