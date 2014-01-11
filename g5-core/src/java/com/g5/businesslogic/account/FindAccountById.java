package com.g5.businesslogic.account;

import com.g5.businesslogic.constraints.Id;
import com.g5.entities.EntityClassHelperLocal;
import com.g5.types.Account;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class FindAccountById implements FindAccountByIdLocal {

    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;
    @Inject
    private EntityClassHelperLocal entityClassHelper;

    @Override
    @RolesAllowed({"Administrators", "Customers"})
    public Account execute(@Id long accountId) {
        return entityManager.
                find(entityClassHelper.getAccountClass(), accountId);
    }

}
