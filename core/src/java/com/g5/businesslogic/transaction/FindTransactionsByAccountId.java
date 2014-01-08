package com.g5.businesslogic.transaction;

import com.g5.entities.EntityClassHelperLocal;
import com.g5.businesslogic.account.AccountValidatorLocal;
import com.g5.businesslogic.constraints.Id;
import com.g5.types.Account;
import com.g5.types.Transaction;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class FindTransactionsByAccountId implements
        FindTransactionsByAccountIdLocal {

    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;
    @Inject
    private EntityClassHelperLocal entityClassHelper;
    @Inject
    private AccountValidatorLocal accountValidator;

    @Override
    public List<Transaction> execute(@Id long accountId) {
        Account account = entityManager.
                find(entityClassHelper.getAccountClass(), accountId);

        accountValidator.exists(account);

        return (List<Transaction>) (List<?>) entityManager.createNamedQuery(
                "Transaction.findByAccount", entityClassHelper.
                getTransactionClass()).setParameter("account", account).
                getResultList();
    }

}
