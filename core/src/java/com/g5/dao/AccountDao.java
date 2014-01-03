package com.g5.dao;

import com.g5.types.Account;
import com.g5.entities.AccountEntity;
import com.g5.types.Customer;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.LockModeType;

@Stateless
public class AccountDao extends AbstractBaseDao<Account, AccountEntity> implements AccountDaoRemote, AccountDaoLocal {

    @Inject
    private TransactionDaoLocal transactionDao;

    @Override
    public List<Account> findByCustomer(Customer customer) {
        return (List<Account>) (List<?>) entityManager.createNamedQuery("Account.findByCustomer", AccountEntity.class).setParameter("customer", customer).getResultList();
    }

    @Override
    public List<Account> findByCustomer(Customer customer, LockModeType lockModeType) {
        return (List<Account>) (List<?>) entityManager.createNamedQuery("Account.findByCustomer", AccountEntity.class).setParameter("customer", customer).setLockMode(lockModeType).getResultList();
    }

}
