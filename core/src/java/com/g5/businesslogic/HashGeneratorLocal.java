package com.g5.businesslogic;

import com.g5.businesslogic.constraints.Hash;
import com.g5.businesslogic.constraints.Password;
import com.g5.businesslogic.constraints.Salt;
import javax.ejb.Local;
import javax.validation.ConstraintTarget;

@Local
public interface HashGeneratorLocal {

    @Hash(validationAppliesTo = ConstraintTarget.RETURN_VALUE)
    String generateHash(@Password final String password, @Salt final String salt);

}
