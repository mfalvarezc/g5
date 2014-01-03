package com.g5.dao;

import com.g5.types.Account;
import com.g5.types.Customer;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.LockModeType;

@Local
public interface AccountDaoLocal extends BaseDaoLocal<Account> {

    public List<Account> findByCustomer(Customer customer);

    public List<Account> findByCustomer(Customer customer, LockModeType lockModeType);

}
