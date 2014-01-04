package com.g5.services;

import com.g5.constraints.Description;
import com.g5.constraints.Id;
import com.g5.constraints.PositiveDecimal;
import com.g5.dao.AccountDaoLocal;
import com.g5.dao.PaymentDaoLocal;
import com.g5.entities.EntityFactoryLocal;
import com.g5.exceptions.NotEnoughFundsException;
import com.g5.services.validators.AccountValidatorLocal;
import com.g5.services.validators.PaymentValidatorLocal;
import com.g5.types.Account;
import com.g5.types.Payment;
import com.g5.types.Transaction;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.LockModeType;
import javax.validation.constraints.NotNull;

@Stateless
public class PaymentService implements PaymentServiceLocal {

    @Inject
    private AccountDaoLocal accountDao;
    @Inject
    private PaymentDaoLocal paymentDao;
    @Inject
    private TransactionServiceLocal transactionService;
    @Inject
    private AccountValidatorLocal accountValidator;
    @Inject
    private PaymentValidatorLocal paymentValidator;
    @Inject
    private EntityFactoryLocal entityFactory;

    @Override
    public void rollbackPayment(@Id long paymentId) {
        Payment payment = paymentDao.find(paymentId);

        paymentValidator.exists(payment);

        Account receiverAccount = payment.getReceiverAccount();
        Account senderAccount = payment.getSenderAccount();

        accountValidator.exists(receiverAccount);
        accountValidator.exists(senderAccount);

        accountDao.lock(receiverAccount, LockModeType.PESSIMISTIC_WRITE);
        accountDao.lock(senderAccount, LockModeType.PESSIMISTIC_WRITE);

        accountValidator.isOpen(receiverAccount);
        accountValidator.isOpen(senderAccount);

        transactionService.create(receiverAccount.getId(), "Rollback - " + payment.getDescription(), payment.getValue().negate());
        transactionService.create(senderAccount.getId(), "Rollback - " + payment.getDescription(), payment.getValue());
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public long requestPayment(@Id final long receiverAccountId, @Description final String description, @PositiveDecimal final BigDecimal value) {
        Account receiverAccount = accountDao.find(receiverAccountId);

        accountValidator.exists(receiverAccount);
        accountValidator.isOpen(receiverAccount);

        Payment payment = entityFactory.createPayment();
        payment.setReceiverAccount(receiverAccount);
        payment.setDescription(description);
        payment.setValue(value);

        paymentDao.persist(payment);

        return payment.getId();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @NotNull
    public Transaction approvePayment(@Id long paymentId, @Id final long senderAccountId, @PositiveDecimal final BigDecimal value) throws NotEnoughFundsException {
        Payment payment = paymentDao.find(paymentId, LockModeType.PESSIMISTIC_WRITE);

        paymentValidator.exists(payment);
        paymentValidator.isNotYetPaid(payment);
        paymentValidator.hasValue(payment, value);

        Account senderAccount = accountDao.find(senderAccountId, LockModeType.PESSIMISTIC_WRITE);

        accountValidator.exists(senderAccount);
        accountValidator.isOpen(senderAccount);
        accountValidator.hasEnoughFunds(senderAccount, value);

        Account receiverAccount = payment.getReceiverAccount();

        accountValidator.exists(receiverAccount);

        accountDao.lock(payment.getReceiverAccount(), LockModeType.PESSIMISTIC_WRITE);

        accountValidator.isOpen(receiverAccount);

        Transaction senderTransaction = transactionService.create(senderAccountId, payment.getDescription(), value.negate());
        Transaction receiverTransaction = transactionService.create(payment.getReceiverAccount().getId(), payment.getDescription(), value);

        payment.setSenderAccount(senderAccount);
        payment.setSenderTransaction(senderTransaction);
        payment.setReceiverTransaction(receiverTransaction);

        return senderTransaction;
    }

}
