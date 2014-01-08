package com.g5.businesslogic.customer;

import com.g5.businesslogic.HashGeneratorLocal;
import com.g5.businesslogic.SaltGeneratorLocal;
import com.g5.businesslogic.constraints.Id;
import com.g5.businesslogic.constraints.Password;
import com.g5.entities.EntityClassHelperLocal;
import com.g5.types.Customer;
import com.g5.types.CustomerCredentials;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ChangeCustomerPassword implements ChangeCustomerPasswordLocal {

    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;
    @Inject
    private EntityClassHelperLocal entityClassHelper;
    @Inject
    private CustomerValidatorLocal customerValidator;
    @Inject
    private SaltGeneratorLocal saltGenerator;
    @Inject
    private HashGeneratorLocal hashGenerator;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void execute(@Id long customerId, @Password String password) {
        Customer customer = entityManager.find(entityClassHelper.
                getCustomerClass(), customerId);

        customerValidator.exists(customer);
        customerValidator.isEnabled(customer);

        CustomerCredentials customerCredentials = entityManager.
                createNamedQuery("CustomerCredentials.findByCustomer",
                        entityClassHelper.getCustomerCredentialsClass()).
                setParameter("customer", customer).getSingleResult();

        customerCredentials.setSalt(saltGenerator.generateSalt());
        customerCredentials.setHash(hashGenerator.generateHash(password,
                customerCredentials.getSalt()));
    }

}
