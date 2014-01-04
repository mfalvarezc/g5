package com.g5.services;

import com.g5.constraints.Key;
import com.g5.constraints.Password;
import com.g5.constraints.Salt;
import javax.ejb.Local;
import javax.validation.ConstraintTarget;

@Local
public interface KeyGeneratorLocal {

    @Key(validationAppliesTo = ConstraintTarget.RETURN_VALUE)
    String generateKey(@Password final String password, @Salt final String salt);

}
