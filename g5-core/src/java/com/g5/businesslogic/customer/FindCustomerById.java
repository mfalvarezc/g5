package com.g5.businesslogic.customer;

import com.g5.businesslogic.constraints.Id;
import com.g5.entities.EntityClassHelperLocal;
import com.g5.types.Customer;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class FindCustomerById implements FindCustomerByIdLocal {

    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;
    @Inject
    private EntityClassHelperLocal entityClassHelper;

    @Override
    @RolesAllowed({"Administrators"})
    public Customer execute(@Id long customerId) {
        return entityManager.find(entityClassHelper.getCustomerClass(),
                customerId);
    }

}
