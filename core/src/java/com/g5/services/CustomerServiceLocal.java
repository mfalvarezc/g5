package com.g5.services;

import com.g5.types.Customer;
import com.g5.constraints.Id;
import com.g5.constraints.Password;
import com.g5.constraints.Username;
import javax.ejb.Local;
import javax.validation.constraints.NotNull;

@Local
public interface CustomerServiceLocal {

    public Customer findById(@Id final long customerId);

    @NotNull
    public Customer create(@Username final String username, @Password final String password);

    public void changePassword(@Id final long id, @Password final String password);

    public void disable(@Id final long id);

    public void enable(@Id final long id);

}
