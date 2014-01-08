package com.g5.businesslogic.transaction;

import com.g5.entities.EntityClassHelperLocal;
import com.g5.entities.EntityFactoryLocal;
import com.g5.businesslogic.constraints.Description;
import com.g5.businesslogic.constraints.Id;
import com.g5.businesslogic.constraints.NotZeroDecimal;
import com.g5.types.Account;
import com.g5.types.Transaction;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CreateTransaction implements CreateTransactionLocal {

    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;
    @Inject
    private EntityClassHelperLocal entityClassHelper;
    @Inject
    private EntityFactoryLocal entityFactory;

    @Override
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Transaction execute(@Id long accountId,
            @Description String description, @NotZeroDecimal BigDecimal value) {
        Account account = entityManager.
                find(entityClassHelper.getAccountClass(), accountId);

        Transaction transaction = entityFactory.createTransaction();

        transaction.setAccount(account);
        transaction.setDate(new Date());
        transaction.setDescription(description);
        transaction.setValue(value);

        entityManager.persist(transaction);

        account = transaction.getAccount();
        account.setBalance(account.getBalance().add(transaction.getValue()));

        return transaction;
    }

}
