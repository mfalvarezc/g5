package com.g5.businesslogic;

import com.g5.exceptions.NotEnoughFundsException;
import com.g5.types.Transaction;
import java.math.BigDecimal;
import javax.ejb.Local;

@Local
public interface PaymentFacadeLocal {

    Transaction approvePayment(long paymentId, final long senderAccountId,
            final BigDecimal value) throws NotEnoughFundsException;

    long requestPayment(final long receiverAccountId, final String description,
            final BigDecimal value);

    void rollbackPayment(final long paymentId);

}
