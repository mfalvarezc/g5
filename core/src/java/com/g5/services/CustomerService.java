package com.g5.services;

import com.g5.dao.AccountDaoLocal;
import com.g5.types.Customer;
import com.g5.types.CustomerCredentials;
import com.g5.dao.CustomerCredentialsDaoLocal;
import com.g5.dao.CustomerDaoLocal;
import com.g5.entities.EntityFactoryLocal;
import com.g5.types.Account;
import com.g5.constraints.Id;
import com.g5.constraints.Password;
import com.g5.constraints.Username;
import com.g5.services.validators.CustomerValidatorLocal;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.LockModeType;
import javax.validation.constraints.NotNull;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CustomerService implements CustomerServiceLocal {

    @Inject
    private CustomerDaoLocal customerDao;
    @Inject
    private CustomerCredentialsDaoLocal customerCredentialsDao;
    @Inject
    private AccountDaoLocal accountDao;
    @Inject
    private EntityFactoryLocal entityFactory;
    @Inject
    private SaltGeneratorLocal saltGenerator;
    @Inject
    private KeyGeneratorLocal keyGenerator;
    @Inject
    private CustomerValidatorLocal customerValidator;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void changePassword(@Id final long id, @Password final String password) {
        Customer customer = customerDao.find(id);

        customerValidator.exists(customer);
        customerValidator.isEnabled(customer);

        CustomerCredentials customerCredentials = customerCredentialsDao.findByCustomer(customer);

        customerCredentials.setSalt(saltGenerator.generateSalt());
        customerCredentials.setKey(keyGenerator.generateKey(password, customerCredentials.getSalt()));

        customerCredentialsDao.merge(customerCredentials);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @NotNull
    public Customer create(@Username final String username, @Password final String password) {
        Customer customer = entityFactory.createCustomer();

        customerDao.persist(customer);

        CustomerCredentials customerCredentials = entityFactory.createCustomerCredentials();

        customerCredentials.setCustomer(customer);
        customerCredentials.setUsername(username);
        customerCredentials.setSalt(saltGenerator.generateSalt());
        customerCredentials.setKey(keyGenerator.generateKey(password, customerCredentials.getSalt()));

        customerCredentialsDao.persist(customerCredentials);

        return customer;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Customer findById(@Id final long id) {
        return customerDao.find(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void disable(@Id final long id) {
        Customer customer = customerDao.find(id, LockModeType.PESSIMISTIC_WRITE);

        customerValidator.exists(customer);
        customerValidator.isEnabled(customer);

        List<Account> accounts = accountDao.findByCustomer(customer, LockModeType.PESSIMISTIC_WRITE);

        customer.setEnabled(false);

        // TODO: Store open accounts to reopen if the customer gets enabled
        for (Account account : accounts) {
            account.setOpen(false);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void enable(@Id final long id) {
        Customer customer = customerDao.find(id, LockModeType.PESSIMISTIC_WRITE);

        customerValidator.exists(customer);
        customerValidator.isDisabled(customer);

        List<Account> accounts = accountDao.findByCustomer(customer, LockModeType.PESSIMISTIC_WRITE);

        customer.setEnabled(true);

        // TODO: Only reopen accounts which were open before disabling the customer
        for (Account account : accounts) {
            account.setOpen(true);
        }
    }

}
