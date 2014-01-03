package com.g5.dao;

import com.g5.types.Account;
import com.g5.types.Customer;
import java.util.List;
import javax.ejb.Remote;
import javax.persistence.LockModeType;

@Remote
public interface AccountDaoRemote extends BaseDaoRemote<Account> {

    public List<Account> findByCustomer(Customer customer);

    public List<Account> findByCustomer(Customer customer, LockModeType lockModeType);

}
