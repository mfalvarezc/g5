package com.g5.dao;

import com.g5.types.Customer;
import com.g5.types.CustomerCredentials;
import com.g5.entities.CustomerCredentialsEntity;
import javax.ejb.Stateless;

@Stateless
public class CustomerCredentialsDao extends AbstractBaseDao<CustomerCredentials, CustomerCredentialsEntity> implements CustomerCredentialsDaoRemote, CustomerCredentialsDaoLocal {

    @Override
    public CustomerCredentials findByCustomer(final Customer customer) {
        return entityManager.createNamedQuery("CustomerCredentials.findByCustomer", CustomerCredentialsEntity.class).setParameter("customer", customer).getSingleResult();
    }

    @Override
    public CustomerCredentials findByUsernameAndKey(final String username, final String key) {
        return entityManager.createNamedQuery("CustomerCredentials.findByUsernameAndKey", CustomerCredentialsEntity.class).setParameter("username", username).setParameter("key", key).getSingleResult();
    }

}
