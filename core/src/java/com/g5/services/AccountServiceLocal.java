package com.g5.services;

import com.g5.types.Account;
import javax.ejb.Local;

@Local
public interface AccountServiceLocal {

    public Account create(final long customerId);

    public Account findById(final long id);

    public void close(final long id);

    public void reopen(final long id);

}
