package com.g5.services;

import com.g5.types.Account;
import com.g5.types.Customer;
import com.g5.dao.AccountDaoLocal;
import com.g5.dao.CustomerDaoLocal;
import com.g5.entities.EntityFactoryLocal;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.LockModeType;

@Stateless
public class AccountService implements AccountServiceRemote, AccountServiceLocal {

    @Inject
    private CustomerDaoLocal customerDao;
    @Inject
    private AccountDaoLocal accountDao;
    @Inject
    private EntityFactoryLocal entityFactory;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Account create(final long customerId) {
        Customer customer = customerDao.find(customerId);

        Account account = entityFactory.createAccount();
        account.setCreationDate(new Date());
        account.setBalance(BigDecimal.ZERO);
        account.setCustomer(customer);

        accountDao.persist(account);

        return account;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Account findById(final long id) {
        return accountDao.find(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void close(final long id) {
        Account account = accountDao.find(id, LockModeType.PESSIMISTIC_WRITE);

        if (!account.isOpen()) {
            throw new IllegalStateException("The account is closed");
        }

        account.setOpen(false);
    }

    @Override
    public void reopen(long id) {
        Account account = accountDao.find(id, LockModeType.PESSIMISTIC_WRITE);

        if (account.isOpen()) {
            throw new IllegalStateException("The account is open");
        }

        account.setOpen(true);
    }

}
