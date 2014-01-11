package com.g5.businesslogic.transaction;

import com.g5.businesslogic.constraints.Id;
import com.g5.entities.EntityClassHelperLocal;
import com.g5.types.Transaction;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class FindTransactionById implements FindTransactionByIdLocal {

    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;
    @Inject
    private EntityClassHelperLocal entityClassHelper;

    @Override
    @RolesAllowed({"Administrators", "Customers"})
    public Transaction execute(@Id long transactionId) {
        return entityManager.find(entityClassHelper.getTransactionClass(),
                transactionId);
    }

}
