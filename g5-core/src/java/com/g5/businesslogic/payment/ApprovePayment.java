package com.g5.businesslogic.payment;

import com.g5.businesslogic.transaction.CreateTransactionLocal;
import com.g5.entities.EntityClassHelperLocal;
import com.g5.exceptions.NotEnoughFundsException;
import com.g5.businesslogic.account.AccountValidatorLocal;
import com.g5.businesslogic.constraints.Id;
import com.g5.businesslogic.constraints.PositiveDecimal;
import com.g5.types.Account;
import com.g5.types.Payment;
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
public class ApprovePayment implements ApprovePaymentLocal {

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
    @RolesAllowed({"Customers"})
    public Transaction execute(@Id long paymentId, @Id long senderAccountId,
            @PositiveDecimal BigDecimal value) throws NotEnoughFundsException {
        Payment payment = entityManager.
                find(entityClassHelper.getPaymentClass(), paymentId,
                        LockModeType.PESSIMISTIC_WRITE);

        paymentValidator.exists(payment);
        paymentValidator.isNotYetPaid(payment);
        paymentValidator.hasValue(payment, value);

        Account senderAccount = entityManager.find(entityClassHelper.
                getAccountClass(), senderAccountId,
                LockModeType.PESSIMISTIC_WRITE);

        accountValidator.exists(senderAccount);
        accountValidator.isOpen(senderAccount);
        accountValidator.hasEnoughFunds(senderAccount, value);

        Account receiverAccount = payment.getReceiverAccount();

        accountValidator.exists(receiverAccount);

        entityManager.lock(receiverAccount, LockModeType.PESSIMISTIC_WRITE);

        accountValidator.isOpen(receiverAccount);

        Transaction senderTransaction = createTransactionProvider.get().execute(
                senderAccount.getId(), payment.getDescription(), value.negate());
        Transaction receiverTransaction = createTransactionProvider.get().
                execute(receiverAccount.getId(), payment.getDescription(), value);

        payment.setSenderAccount(senderAccount);
        payment.setSenderTransaction(senderTransaction);
        payment.setReceiverTransaction(receiverTransaction);

        return senderTransaction;
    }

}
