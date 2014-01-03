package com.g5.dao;

import com.g5.types.Customer;
import com.g5.types.CustomerCredentials;
import javax.ejb.Remote;

@Remote
public interface CustomerCredentialsDaoRemote extends BaseDaoRemote<CustomerCredentials> {

    public CustomerCredentials findByCustomer(final Customer customer);

    public CustomerCredentials findByUsernameAndKey(final String username, final String key);
}
