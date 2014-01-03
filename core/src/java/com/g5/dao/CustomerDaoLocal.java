package com.g5.dao;

import com.g5.types.Account;
import com.g5.types.Customer;
import javax.ejb.Local;

@Local
public interface CustomerDaoLocal extends BaseDaoLocal<Customer> {

    public Customer findByAccount(final Account account);

}
