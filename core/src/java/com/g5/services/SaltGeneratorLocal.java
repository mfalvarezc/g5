package com.g5.services;

import javax.ejb.Local;

@Local
public interface SaltGeneratorLocal {

    String generateSalt();
    
}
