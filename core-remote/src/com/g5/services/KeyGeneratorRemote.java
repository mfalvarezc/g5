package com.g5.services;

import javax.ejb.Remote;

@Remote
public interface KeyGeneratorRemote {

    public String generateKey(final String password, final String salt);
    
}
