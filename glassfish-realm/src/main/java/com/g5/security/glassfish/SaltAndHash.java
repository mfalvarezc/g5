package com.g5.security.glassfish;

public class SaltAndHash {

    private final String salt;
    private final String hash;

    public SaltAndHash(String salt, String password) {
        this.salt = salt;
        this.hash = password;
    }

    public String getSalt() {
        return salt;
    }

    public String getHash() {
        return hash;
    }

}
