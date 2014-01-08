package com.g5.businesslogic;

import com.g5.businesslogic.constraints.Salt;
import javax.ejb.Local;

@Local
public interface SaltGeneratorLocal {

    //@Salt
    String generateSalt();

}
