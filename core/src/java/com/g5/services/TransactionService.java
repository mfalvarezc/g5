package com.g5.services;

import com.g5.types.Account;
import com.g5.types.Transaction;
import com.g5.dao.AccountDaoLocal;
import com.g5.dao.TransactionDaoLocal;
import com.g5.entities.EntityFactoryLocal;
import com.g5.exceptions.NotEnoughFundsException;
import com.g5.constraints.Description;
import com.g5.constraints.Id;
import com.g5.constraints.NotZeroDecimal;
import com.g5.constraints.PositiveDecimal;
import com.g5.services.validators.AccountValidatorLocal;
import com.g5.services.validators.TransactionValidatorLocal;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.LockModeType;
import javax.validation.constraints.NotNull;

@Stateless
public class TransactionService implements TransactionServiceLocal {

    @Inject
    private EntityFactoryLocal entityFactory;
    @Inject
    private AccountDaoLocal accountDao;
    @Inject
    private TransactionDaoLocal transactionDao;
    @Inject
    private AccountValidatorLocal accountValidator;
    @Inject
    private TransactionValidatorLocal transactionValidator;

    @Override
    public Transaction create(@Id final long accountId, @Description String description, @NotZeroDecimal final BigDecimal value) {
        Account account = accountDao.find(accountId);

        Transaction transaction = entityFactory.createTransaction();

        transaction.setAccount(account);
        transaction.setDate(new Date());
        transaction.setDescription(description);
        transaction.setValue(value);

        transactionDao.persist(transaction);

        account = transaction.getAccount();
        account.setBalance(account.getBalance().add(transaction.getValue()));

        return transaction;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void rollbackTransaction(@Id final long transactionId) {
        Transaction transaction = transactionDao.find(transactionId, LockModeType.PESSIMISTIC_WRITE);

        transactionValidator.exists(transaction);
        transactionValidator.doesNotBelongToPayment(transaction);

        Account account = transaction.getAccount();

        accountValidator.exists(account);

        accountDao.lock(account, LockModeType.PESSIMISTIC_WRITE);

        accountValidator.isOpen(account);

        create(account.getId(), "Rollback - " + transaction.getDescription(), transaction.getValue().negate());
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @NotNull
    public Transaction deposit(@Id final long accountId, @PositiveDecimal final BigDecimal value) {
        Account account = accountDao.find(accountId, LockModeType.PESSIMISTIC_WRITE);

        accountValidator.exists(account);
        accountValidator.isOpen(account);

        return create(accountId, "Deposit", value);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @NotNull
    public Transaction withdraw(@Id final long accountId, @PositiveDecimal final BigDecimal value) throws NotEnoughFundsException {
        Account account = accountDao.find(accountId, LockModeType.PESSIMISTIC_WRITE);

        accountValidator.exists(account);
        accountValidator.isOpen(account);
        accountValidator.hasEnoughFunds(account, value);

        return create(accountId, "Withdraw", value.negate());
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Transaction findById(@Id final long id) {
        return transactionDao.find(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @NotNull
    public List<Transaction> findByAccountId(@Id final long accountId) {
        Account account = accountDao.find(accountId);

        accountValidator.exists(account);

        return transactionDao.findByAccount(account);
    }

}
