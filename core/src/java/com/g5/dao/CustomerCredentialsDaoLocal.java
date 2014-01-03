package com.g5.dao;

import com.g5.types.Customer;
import com.g5.types.CustomerCredentials;
import javax.ejb.Local;

@Local
public interface CustomerCredentialsDaoLocal extends BaseDaoLocal<CustomerCredentials> {

    public CustomerCredentials findByCustomer(final Customer customer);

    public CustomerCredentials findByUsernameAndKey(final String username, final String key);
    
}
