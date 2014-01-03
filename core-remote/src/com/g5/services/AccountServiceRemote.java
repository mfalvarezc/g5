package com.g5.services;

import com.g5.types.Account;
import javax.ejb.Remote;

@Remote
public interface AccountServiceRemote {

    public Account create(final long customerId);

    public Account findById(final long id);

    public void close(final long id);
    
    public void reopen(final long id);
    
}
