package com.g5.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.DatatypeConverter;

@Stateless
public class SaltGenerator implements SaltGeneratorRemote, SaltGeneratorLocal {
    
    private final static String ALGORITHM = "SHA1PRNG";
    private final static int SALT_LENGTH = 24;
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        try {
            SecureRandom.getInstance(ALGORITHM).nextBytes(salt);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SaltGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return DatatypeConverter.printBase64Binary(salt);
    }
    
}
