package fr.inria.diverse.mobileprivacyprofilerserver.utils;

import org.junit.Test;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class AuthenticationUtilTest {

    @Test
    public void hash() {
        char[] password = "password".toCharArray();
        String salt = "salt";

        byte[] hashPassword;
        PBEKeySpec spec = new PBEKeySpec(password, salt.getBytes(), AuthenticationUtil.ITERATIONS, AuthenticationUtil.KEY_LENGTH);
        //Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(AuthenticationUtil.ALGORITHM);
            hashPassword =  skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }

        assertArrayEquals(hashPassword,AuthenticationUtil.INSTANCE.hash(password,salt.getBytes()));
    }


    @Test
    public void isExpectedPassword() {
        String password = "password";
        String salt = "salt";

        byte[] hashPassword = AuthenticationUtil.INSTANCE.hash(password.toCharArray(),salt.getBytes());
        assertTrue(AuthenticationUtil.INSTANCE.isExpectedPassword(password.toCharArray(), salt.getBytes(), hashPassword));
    }


}