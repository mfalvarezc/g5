package com.g5.dao;

import com.g5.types.Account;
import com.g5.types.Customer;
import javax.ejb.Remote;

@Remote
public interface CustomerDaoRemote extends BaseDaoRemote<Customer> {

    public Customer findByAccount(final Account account);

}
