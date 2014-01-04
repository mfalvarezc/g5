package com.g5.dao;

import com.g5.types.Account;
import com.g5.types.Customer;
import com.g5.entities.CustomerEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
public class CustomerDao extends AbstractBaseDao<Customer, CustomerEntity> implements CustomerDaoLocal {

    @Inject
    private CustomerCredentialsDaoLocal customerCredentialsDao;
    @Inject
    private AccountDaoLocal accountDao;

    @Override
    public Customer findByAccount(final Account account) {
        return entityManager.createNamedQuery("Customer.findByAccount", CustomerEntity.class).setParameter("account", account).getSingleResult();
    }

}
