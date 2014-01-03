package com.g5.services;

import javax.ejb.Remote;

@Remote
public interface SaltGeneratorRemote {

    public String generateSalt();

}
