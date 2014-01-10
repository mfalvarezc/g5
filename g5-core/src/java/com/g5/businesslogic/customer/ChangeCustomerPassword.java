package com.g5.businesslogic.customer;

import com.g5.businesslogic.constraints.Id;
import com.g5.businesslogic.constraints.Password;
import com.g5.entities.EntityClassHelperLocal;
import com.g5.security.HashGenerator;
import com.g5.security.SaltGenerator;
import com.g5.types.Customer;
import com.g5.types.User;
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

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void execute(@Id long customerId, @Password String password) {
        Customer customer = entityManager.find(entityClassHelper.
                getCustomerClass(), customerId);

        customerValidator.exists(customer);
        customerValidator.isEnabled(customer);

        User user = customer.getUser();

        user.setSalt(SaltGenerator.generateSalt());
        user.setHash(HashGenerator.generateHash(password,
                user.getSalt()));
    }

}
