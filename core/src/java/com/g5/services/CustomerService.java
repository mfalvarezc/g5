package com.g5.services;

import com.g5.dao.AccountDaoLocal;
import com.g5.types.Customer;
import com.g5.types.CustomerCredentials;
import com.g5.dao.CustomerCredentialsDaoLocal;
import com.g5.dao.CustomerDaoLocal;
import com.g5.entities.EntityFactoryLocal;
import com.g5.types.Account;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.LockModeType;

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

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void changePassword(final long id, final String password) {
        checkPassword(password);

        Customer customer = customerDao.find(id);

        CustomerCredentials customerCredentials = customerCredentialsDao.findByCustomer(customer);

        customerCredentials.setSalt(saltGenerator.generateSalt());
        customerCredentials.setKey(keyGenerator.generateKey(password, customerCredentials.getSalt()));

        customerCredentialsDao.merge(customerCredentials);
    }

    private void checkPassword(String password) {
        // TODO: Check password
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Customer create(final String username, String password) {
        checkUsername(username);
        checkPassword(password);

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

    private void checkUsername(String username) {
        // TODO: Check username
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Customer findById(final long id) {
        return customerDao.find(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void disable(final long id) {
        Customer customer = customerDao.find(id, LockModeType.PESSIMISTIC_WRITE);

        if (!customer.isEnabled()) {
            throw new IllegalStateException("The customer is disabled");
        }

        List<Account> accounts = accountDao.findByCustomer(customer, LockModeType.PESSIMISTIC_WRITE);

        customer.setEnabled(false);

        for (Account account : accounts) {
            account.setOpen(false);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void enable(final long id) {
        Customer customer = customerDao.find(id, LockModeType.PESSIMISTIC_WRITE);

        if (customer.isEnabled()) {
            throw new IllegalStateException("The customer is enabled");
        }

        List<Account> accounts = accountDao.findByCustomer(customer, LockModeType.PESSIMISTIC_WRITE);

        customer.setEnabled(true);

        for (Account account : accounts) {
            account.setOpen(true);
        }
    }

}
