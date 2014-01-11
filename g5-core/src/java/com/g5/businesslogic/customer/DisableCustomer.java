package com.g5.businesslogic.customer;

import com.g5.businesslogic.constraints.Id;
import com.g5.entities.EntityClassHelperLocal;
import com.g5.types.Account;
import com.g5.types.Customer;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

@Stateless
public class DisableCustomer implements DisableCustomerLocal {

    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;
    @Inject
    private EntityClassHelperLocal entityClassHelper;
    @Inject
    private CustomerValidatorLocal customerValidator;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @RolesAllowed({"Administrators"})
    public void execute(@Id long customerId) {
        Customer customer = entityManager.find(entityClassHelper.
                getCustomerClass(), customerId, LockModeType.PESSIMISTIC_WRITE);

        customerValidator.exists(customer);
        customerValidator.isEnabled(customer);

        List<? extends Account> accounts = entityManager.createNamedQuery(
                "Account.findByCustomer", entityClassHelper.getAccountClass()).
                setParameter("customer", customer).getResultList();

        customer.setEnabled(false);

        // TODO: Store open accounts to reopen if the customer gets enabled
        for (Account account : accounts) {
            account.setOpen(false);
        }
    }

}
