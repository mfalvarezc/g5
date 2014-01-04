package com.g5.services;

import com.g5.constraints.Salt;
import javax.ejb.Local;

@Local
public interface SaltGeneratorLocal {

    //@Salt
    String generateSalt();
    
}
