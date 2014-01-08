package com.g5.businesslogic.customer;

import com.g5.businesslogic.HashGeneratorLocal;
import com.g5.businesslogic.SaltGeneratorLocal;
import com.g5.businesslogic.constraints.Password;
import com.g5.businesslogic.constraints.Username;
import com.g5.entities.EntityFactoryLocal;
import com.g5.types.Customer;
import com.g5.types.CustomerCredentials;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CreateCustomer implements CreateCustomerLocal {

    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;
    @Inject
    private EntityFactoryLocal entityFactory;
    @Inject
    private SaltGeneratorLocal saltGenerator;
    @Inject
    private HashGeneratorLocal hashGenerator;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Customer execute(@Username String username, @Password String password) {
        Customer customer = entityFactory.createCustomer();

        entityManager.persist(customer);

        CustomerCredentials customerCredentials = entityFactory.
                createCustomerCredentials();

        customerCredentials.setCustomer(customer);
        customerCredentials.setUsername(username);
        customerCredentials.setSalt(saltGenerator.generateSalt());
        customerCredentials.setHash(hashGenerator.generateHash(password,
                customerCredentials.getSalt()));

        entityManager.persist(customerCredentials);

        return customer;
    }

}
