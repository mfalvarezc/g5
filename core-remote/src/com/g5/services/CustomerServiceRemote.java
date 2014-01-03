package com.g5.services;

import com.g5.types.Customer;
import javax.ejb.Remote;

@Remote
public interface CustomerServiceRemote {

    public Customer findById(final long customerId);

    public Customer create(final String username, String password);

    public void changePassword(final long id, final String password);

    public void disable(final long id);
    
    public void enable(final long id);

}
