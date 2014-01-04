package com.g5.services;

import com.g5.constraints.Description;
import com.g5.constraints.Id;
import com.g5.constraints.PositiveDecimal;
import com.g5.exceptions.NotEnoughFundsException;
import com.g5.types.Transaction;
import java.math.BigDecimal;
import javax.ejb.Local;
import javax.validation.constraints.NotNull;

@Local
public interface PaymentServiceLocal {

    public void rollbackPayment(@Id final long paymentId);

    public long requestPayment(@Id final long receiverAccountId, @Description final String description, @PositiveDecimal final BigDecimal value);

    @NotNull
    public Transaction approvePayment(@Id long paymentId, @Id final long senderAccountId, @PositiveDecimal final BigDecimal value) throws NotEnoughFundsException;
}
