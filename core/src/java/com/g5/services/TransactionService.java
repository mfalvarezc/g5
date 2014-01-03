package com.g5.services;

import com.g5.types.Account;
import com.g5.types.Transaction;
import com.g5.dao.AccountDaoLocal;
import com.g5.dao.PaymentDaoLocal;
import com.g5.dao.TransactionDaoLocal;
import com.g5.entities.EntityFactoryLocal;
import com.g5.exceptions.NotEnoughFundsException;
import com.g5.types.Payment;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.LockModeType;

@Stateless
public class TransactionService implements TransactionServiceRemote, TransactionServiceLocal {

    @Inject
    private EntityFactoryLocal entityFactory;
    @Inject
    private AccountDaoLocal accountDao;
    @Inject
    private PaymentDaoLocal paymentDao;
    @Inject
    private TransactionDaoLocal transactionDao;

    private Transaction create(final long accountId, String description, final BigDecimal value) {
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
    public void rollbackTransaction(final long transactionId) {
        Transaction transaction = transactionDao.find(transactionId, LockModeType.PESSIMISTIC_WRITE);

        Payment payment = paymentDao.findByTransaction(transaction);

        if (payment != null) {
            throw new UnsupportedOperationException("The transaction belongs to a payment. The rollback must be performed on the payment.");
        }

        Account account = transaction.getAccount();

        accountDao.lock(account, LockModeType.PESSIMISTIC_WRITE);

        create(account.getId(), "Rollback - " + transaction.getDescription(), transaction.getValue().negate());
    }

    @Override
    public void rollbackPayment(long paymentId) {
        Payment payment = paymentDao.find(paymentId);

        Account receiverAccount = payment.getReceiverAccount();
        Account senderAccount = payment.getSenderAccount();

        accountDao.lock(receiverAccount, LockModeType.PESSIMISTIC_WRITE);
        accountDao.lock(senderAccount, LockModeType.PESSIMISTIC_WRITE);

        create(receiverAccount.getId(), "Rollback - " + payment.getDescription(), payment.getValue().negate());
        create(senderAccount.getId(), "Rollback - " + payment.getDescription(), payment.getValue());
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Transaction deposit(final long accountId, final BigDecimal value) {
        checkValue(value);

        accountDao.find(accountId, LockModeType.PESSIMISTIC_WRITE);

        return create(accountId, "Deposit", value);
    }

    private void checkValue(BigDecimal value) {
        if (value.signum() != 1) {
            throw new IllegalArgumentException("The value must be greater than zero");
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Transaction withdraw(final long accountId, final BigDecimal value) throws NotEnoughFundsException {
        checkValue(value);

        Account account = accountDao.find(accountId, LockModeType.PESSIMISTIC_WRITE);

        if (account.getBalance().compareTo(value) < 0) {
            throw new NotEnoughFundsException();
        }

        return create(accountId, "Withdraw", value.negate());
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Transaction findById(final long id) {
        return transactionDao.find(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Transaction> findByAccountId(final long accountId) {
        Account account = accountDao.find(accountId);
        return transactionDao.findByAccount(account);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public long requestPayment(final long receiverAccountId, final String description, final BigDecimal value) {
        checkValue(value);

        Account receiverAccount = accountDao.find(receiverAccountId);

        Payment payment = entityFactory.createPayment();
        payment.setReceiverAccount(receiverAccount);
        payment.setDescription(description);
        payment.setValue(value);

        paymentDao.persist(payment);

        return payment.getId();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Transaction approvePayment(long paymentId, final long senderAccountId, final BigDecimal value) {
        checkValue(value);

        Payment payment = paymentDao.find(paymentId, LockModeType.PESSIMISTIC_WRITE);

        if (payment.getReceiverTransaction() != null || payment.getSenderAccount() != null || payment.getSenderTransaction() != null) {
            throw new RuntimeException("The payment is already associated with a sender account or a transaction");
        }

        if (value.compareTo(payment.getValue()) != 0) {
            throw new IllegalArgumentException("The payment value is not correct");
        }

        Account senderAccount = accountDao.find(senderAccountId, LockModeType.PESSIMISTIC_WRITE);

        if (senderAccount.getBalance().compareTo(value) < 0) {
            throw new NotEnoughFundsException();
        }

        accountDao.lock(payment.getReceiverAccount(), LockModeType.PESSIMISTIC_WRITE);

        Transaction senderTransaction = create(senderAccountId, payment.getDescription(), value.negate());
        Transaction receiverTransaction = create(payment.getReceiverAccount().getId(), payment.getDescription(), value);

        transactionDao.persist(senderTransaction);
        transactionDao.persist(receiverTransaction);

        payment.setSenderAccount(senderAccount);
        payment.setSenderTransaction(senderTransaction);
        payment.setReceiverTransaction(receiverTransaction);

        return senderTransaction;
    }

}
