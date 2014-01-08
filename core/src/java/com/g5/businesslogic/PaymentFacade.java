package com.g5.businesslogic;

import com.g5.businesslogic.payment.ApprovePaymentLocal;
import com.g5.businesslogic.payment.RequestPaymentLocal;
import com.g5.businesslogic.payment.RollbackPaymentLocal;
import com.g5.exceptions.NotEnoughFundsException;
import com.g5.types.Transaction;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Provider;

@Stateless
public class PaymentFacade implements PaymentFacadeLocal {

    @Inject
    private Provider<ApprovePaymentLocal> approvePaymentProvider;
    @Inject
    private Provider<RequestPaymentLocal> requestPaymentProvider;
    @Inject
    private Provider<RollbackPaymentLocal> rollbackPaymentProvider;

    @Override
    public Transaction approvePayment(long paymentId, long senderAccountId,
            BigDecimal value) throws NotEnoughFundsException {
        return approvePaymentProvider.get().execute(paymentId, senderAccountId,
                value);
    }

    @Override
    public long requestPayment(long receiverAccountId, String description,
            BigDecimal value) {
        return requestPaymentProvider.get().execute(receiverAccountId,
                description, value);
    }

    @Override
    public void rollbackPayment(long paymentId) {
        rollbackPaymentProvider.get().execute(paymentId);
    }

}
