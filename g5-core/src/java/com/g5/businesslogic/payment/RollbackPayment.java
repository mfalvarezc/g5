package com.g5.businesslogic.payment;

import com.g5.businesslogic.transaction.CreateTransactionLocal;
import com.g5.entities.EntityClassHelperLocal;
import com.g5.businesslogic.account.AccountValidatorLocal;
import com.g5.businesslogic.constraints.Id;
import com.g5.types.Account;
import com.g5.types.Payment;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

@Stateless
public class RollbackPayment implements RollbackPaymentLocal {

    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;
    @Inject
    private EntityClassHelperLocal entityClassHelper;
    @Inject
    private PaymentValidatorLocal paymentValidator;
    @Inject
    private AccountValidatorLocal accountValidator;
    @Inject
    private Provider<CreateTransactionLocal> createTransactionProvider;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void execute(@Id long paymentId) {
        Payment payment = entityManager.
                find(entityClassHelper.getPaymentClass(), paymentId);

        paymentValidator.exists(payment);

        Account receiverAccount = payment.getReceiverAccount();
        Account senderAccount = payment.getSenderAccount();

        accountValidator.exists(receiverAccount);
        accountValidator.exists(senderAccount);

        entityManager.lock(receiverAccount, LockModeType.PESSIMISTIC_WRITE);
        entityManager.lock(senderAccount, LockModeType.PESSIMISTIC_WRITE);

        accountValidator.isOpen(receiverAccount);
        accountValidator.isOpen(senderAccount);

        String description = "Rollback - " + payment.getDescription();

        createTransactionProvider.get().execute(receiverAccount.getId(),
                description, payment.getValue().negate());
        createTransactionProvider.get().execute(senderAccount.getId(),
                description, payment.getValue());
    }

}
