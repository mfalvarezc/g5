package com.g5.businesslogic.payment;

import com.g5.types.Payment;
import java.math.BigDecimal;
import javax.ejb.Stateless;

@Stateless
public class PaymentValidator implements PaymentValidatorLocal {

    @Override
    public void isNotYetPaid(final Payment payment) {
        if (payment.getReceiverTransaction() != null || payment.
                getSenderAccount() != null || payment.getSenderTransaction() !=
                null) {
            throw new IllegalStateException(
                    "The payment is already associated with a sender account or a transaction.");
        }
    }

    @Override
    public void hasValue(final Payment payment, final BigDecimal value) {
        if (payment.getValue().compareTo(value) != 0) {
            throw new IllegalArgumentException(
                    "The payment value is not correct.");
        }
    }

    @Override
    public void exists(final Payment payment) {
        if (payment == null || payment.getId() <= 0) {
            throw new IllegalArgumentException("The payment does not exists.");
        }
    }

}
