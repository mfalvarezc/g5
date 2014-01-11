package com.g5.businesslogic.transaction;

import com.g5.businesslogic.account.AccountValidatorLocal;
import com.g5.businesslogic.constraints.Id;
import com.g5.entities.EntityClassHelperLocal;
import com.g5.types.Account;
import com.g5.types.Transaction;
import java.math.BigDecimal;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

@Stateless
public class RollbackTransaction implements RollbackTransactionLocal {

    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;
    @Inject
    private EntityClassHelperLocal entityClassHelper;
    @Inject
    private TransactionValidatorLocal transactionValidator;
    @Inject
    private AccountValidatorLocal accountValidator;
    @Inject
    private Provider<CreateTransactionLocal> createTransactionProvider;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @RolesAllowed({"Administrators"})
    public void execute(@Id long transactionId) {
        Transaction transaction = entityManager.find(entityClassHelper.
                getTransactionClass(), transactionId);

        transactionValidator.exists(transaction);
        transactionValidator.doesNotBelongToPayment(transaction);

        Account account = transaction.getAccount();

        accountValidator.exists(account);

        entityManager.lock(account, LockModeType.PESSIMISTIC_WRITE);

        accountValidator.isOpen(account);

        long accountId = account.getId();
        String description = "Rollback - " + transaction.getDescription();
        BigDecimal value = transaction.getValue().negate();

        createTransactionProvider.get().execute(accountId, description, value);
    }

}
