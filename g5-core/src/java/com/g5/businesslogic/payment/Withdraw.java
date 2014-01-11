package com.g5.businesslogic.payment;

import com.g5.businesslogic.transaction.CreateTransactionLocal;
import com.g5.entities.EntityClassHelperLocal;
import com.g5.exceptions.NotEnoughFundsException;
import com.g5.businesslogic.account.AccountValidatorLocal;
import com.g5.businesslogic.constraints.Id;
import com.g5.businesslogic.constraints.PositiveDecimal;
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
public class Withdraw implements WithdrawLocal {

    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;
    @Inject
    private EntityClassHelperLocal entityClassHelper;
    @Inject
    private AccountValidatorLocal accountValidator;
    @Inject
    private Provider<CreateTransactionLocal> createTransactionProvider;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @RolesAllowed({"Customers"})
    public Transaction execute(@Id long accountId,
            @PositiveDecimal BigDecimal value) throws NotEnoughFundsException {
        Account account = entityManager.
                find(entityClassHelper.getAccountClass(), accountId,
                        LockModeType.PESSIMISTIC_WRITE);

        accountValidator.exists(account);
        accountValidator.isOpen(account);
        accountValidator.hasEnoughFunds(account, value);

        return createTransactionProvider.get().execute(accountId, "Withdraw",
                value.negate());
    }

}
