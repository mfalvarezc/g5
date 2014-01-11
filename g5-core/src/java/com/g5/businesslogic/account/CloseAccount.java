package com.g5.businesslogic.account;

import com.g5.businesslogic.constraints.Id;
import com.g5.entities.EntityClassHelperLocal;
import com.g5.types.Account;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CloseAccount implements CloseAccountLocal {

    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;
    @Inject
    private AccountValidatorLocal accountValidator;
    @Inject
    private EntityClassHelperLocal entityClassHelper;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @RolesAllowed({"Administrators"})
    public void execute(@Id long accountId) {
        Account account = entityManager.
                find(entityClassHelper.getAccountClass(), accountId);

        accountValidator.exists(account);
        accountValidator.isOpen(account);

        account.setOpen(false);
    }
}
