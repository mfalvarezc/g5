package com.g5.businesslogic;

import com.g5.types.Account;
import javax.ejb.Local;

@Local
public interface AccountFacadeLocal {

    void closeAccount(long accountId);

    Account createAccount(long customerId);

    Account findAccountById(long accountId);

    void reopenAccount(long accountId);

}
