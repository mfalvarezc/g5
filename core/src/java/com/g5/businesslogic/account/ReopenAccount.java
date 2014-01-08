package com.g5.businesslogic.account;

import com.g5.businesslogic.constraints.Id;
import com.g5.entities.EntityClassHelperLocal;
import com.g5.types.Account;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ReopenAccount implements ReopenAccountLocal {

    @Inject
    private EntityClassHelperLocal entityClassHelper;
    @Inject
    private AccountValidatorLocal accountValidator;
    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void execute(@Id long accountId) {
        Account account = entityManager.
                find(entityClassHelper.getAccountClass(), accountId);

        accountValidator.exists(account);
        accountValidator.isClosed(account);

        account.setOpen(true);
    }

}
