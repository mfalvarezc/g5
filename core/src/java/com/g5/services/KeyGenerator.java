package com.g5.services;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.DatatypeConverter;

@Stateless
public class KeyGenerator implements KeyGeneratorLocal {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int ITERATIONS = 1000;
    private static final int KEY_LENGTH = 192;

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String generateKey(final String password, final String salt) {
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), DatatypeConverter.parseBase64Binary(salt), ITERATIONS, KEY_LENGTH);
        SecretKey secretKey = null;

        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            secretKey = secretKeyFactory.generateSecret(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(KeyGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return DatatypeConverter.printBase64Binary(secretKey.getEncoded());
    }

}
