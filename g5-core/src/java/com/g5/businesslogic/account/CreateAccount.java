package com.g5.businesslogic.account;

import com.g5.businesslogic.constraints.Id;
import com.g5.entities.EntityClassHelperLocal;
import com.g5.entities.EntityFactoryLocal;
import com.g5.businesslogic.customer.CustomerValidatorLocal;
import com.g5.types.Account;
import com.g5.types.Customer;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CreateAccount implements CreateAccountLocal {

    @Inject
    private EntityClassHelperLocal entityClassHelper;
    @Inject
    private CustomerValidatorLocal customerValidator;
    @Inject
    private EntityFactoryLocal entityFactory;
    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @RolesAllowed({"Administrators"})
    public Account execute(@Id long customerId) {
        Customer customer = entityManager.find(entityClassHelper.
                getCustomerClass(), customerId);

        customerValidator.exists(customer);
        customerValidator.isEnabled(customer);

        Account account = entityFactory.createAccount();
        account.setCreationDate(new Date());
        account.setBalance(BigDecimal.ZERO);
        account.setCustomer(customer);

        entityManager.persist(account);

        return account;
    }

}
