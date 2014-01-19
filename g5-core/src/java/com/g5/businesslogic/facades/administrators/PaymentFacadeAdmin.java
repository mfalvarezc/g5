/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.g5.businesslogic.facades.administrators;

import com.g5.businesslogic.payment.ApprovePaymentLocal;
import com.g5.businesslogic.payment.RequestPaymentLocal;
import com.g5.businesslogic.payment.RollbackPaymentLocal;
import com.g5.exceptions.NotEnoughFundsException;
import com.g5.types.Transaction;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Provider;

/**
 *
 * @author Juancho
 */
@Stateless
public class PaymentFacadeAdmin implements PaymentFacadeAdminLocal {

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
