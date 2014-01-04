package com.g5.services;

import com.g5.types.Account;
import com.g5.types.Customer;
import com.g5.dao.AccountDaoLocal;
import com.g5.dao.CustomerDaoLocal;
import com.g5.entities.EntityFactoryLocal;
import com.g5.constraints.Id;
import com.g5.services.validators.AccountValidatorLocal;
import com.g5.services.validators.CustomerValidatorLocal;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.LockModeType;
import javax.validation.constraints.NotNull;

@Stateless
public class AccountService implements AccountServiceLocal {

    @Inject
    private CustomerDaoLocal customerDao;
    @Inject
    private AccountDaoLocal accountDao;
    @Inject
    private EntityFactoryLocal entityFactory;
    @Inject
    private CustomerValidatorLocal customerValidator;
    @Inject
    private AccountValidatorLocal accountValidator;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @NotNull
    public Account create(@Id final long customerId) {
        Customer customer = customerDao.find(customerId);

        customerValidator.exists(customer);
        customerValidator.isEnabled(customer);

        Account account = entityFactory.createAccount();
        account.setCreationDate(new Date());
        account.setBalance(BigDecimal.ZERO);
        account.setCustomer(customer);

        accountDao.persist(account);

        return account;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Account findById(@Id final long id) {
        return accountDao.find(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void close(@Id final long id) {
        Account account = accountDao.find(id, LockModeType.PESSIMISTIC_WRITE);

        accountValidator.exists(account);
        accountValidator.isOpen(account);

        account.setOpen(false);
    }

    @Override
    public void reopen(@Id long id) {
        Account account = accountDao.find(id, LockModeType.PESSIMISTIC_WRITE);

        accountValidator.exists(account);
        accountValidator.isClosed(account);

        account.setOpen(true);
    }

}
