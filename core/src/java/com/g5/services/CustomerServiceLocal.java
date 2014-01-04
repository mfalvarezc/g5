package com.g5.services;

import com.g5.types.Customer;
import com.g5.validation.Id;
import com.g5.validation.Password;
import com.g5.validation.Username;
import javax.ejb.Local;

@Local
public interface CustomerServiceLocal {

    public Customer findById(@Id final long customerId);

    public Customer create(@Username final String username, @Password final String password);

    public void changePassword(@Id final long id, @Password final String password);

    public void disable(@Id final long id);
    
    public void enable(@Id final long id);
    
}
