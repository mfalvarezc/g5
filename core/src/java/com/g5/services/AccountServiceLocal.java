package com.g5.services;

import com.g5.types.Account;
import com.g5.constraints.Id;
import javax.ejb.Local;
import javax.validation.constraints.NotNull;

@Local
public interface AccountServiceLocal {

    @NotNull
    public Account create(@Id final long customerId);

    public Account findById(@Id final long id);

    public void close(@Id final long id);

    public void reopen(@Id final long id);

}
