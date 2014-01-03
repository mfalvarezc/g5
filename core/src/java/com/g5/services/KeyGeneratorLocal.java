package com.g5.services;

import javax.ejb.Local;

@Local
public interface KeyGeneratorLocal {

    String generateKey(final String password, final String salt);

}
