package com.g5.dao;

import com.g5.types.Account;
import com.g5.types.Transaction;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface TransactionDaoRemote extends BaseDaoRemote<Transaction> {

    public List<Transaction> findByAccount(final Account account);
    
    public void removeByAccount(final Account account);

}
