package com.g5.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

public class HashGenerator {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int ITERATIONS = 1000;
    private static final int HASH_LENGTH = 192;

    public static String generateHash(String password, String salt) {
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(),
                DatatypeConverter.parseBase64Binary(salt), ITERATIONS,
                HASH_LENGTH);
        SecretKey secretKey = null;

        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(
                    ALGORITHM);
            secretKey = secretKeyFactory.generateSecret(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(HashGenerator.class.getName()).log(Level.SEVERE,
                    null, ex);
            throw new RuntimeException(ex);
        }

        return DatatypeConverter.printBase64Binary(secretKey.getEncoded());
    }

}
