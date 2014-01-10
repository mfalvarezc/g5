package com.g5.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

public final class SaltGenerator {

    private final static String ALGORITHM = "SHA1PRNG";
    private final static int SALT_LENGTH = 24;

    public static String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        try {
            SecureRandom.getInstance(ALGORITHM).nextBytes(salt);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SaltGenerator.class.getName()).log(Level.SEVERE,
                    null, ex);
            throw new RuntimeException(ex);
        }
        return DatatypeConverter.printBase64Binary(salt);
    }

}
