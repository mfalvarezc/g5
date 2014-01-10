package com.g5.webserviceclient;

import java.io.File;
import java.security.SecureRandom;
import javax.xml.bind.DatatypeConverter;

public class TestHelper {

    private static final String TRUST_STORE_PROPERTY =
            "javax.net.ssl.trustStore";
    private static final String TRUST_STORE_PASSWORD_PROPERTY =
            "javax.net.ssl.trustStorePassword";
    private static final String TRUST_STORE_PATH = "./src/META-INF/cacerts.jks";
    private static final String TRUST_STORE_PASSWORD = "changeit";

    public static void configureTrustStore() {
        if (System.getProperty(TRUST_STORE_PROPERTY) == null) {
            System.setProperty(TRUST_STORE_PROPERTY,
                    (new File(TRUST_STORE_PATH)).getAbsolutePath());
            System.setProperty(TRUST_STORE_PASSWORD_PROPERTY,
                    TRUST_STORE_PASSWORD);
        }
    }

    public static String getRandomUsername() {
        return "username-" + Math.abs((new SecureRandom()).nextInt());
    }

}
