package com.g5.services;

import com.g5.exceptions.NotEnoughFundsException;
import com.g5.types.Transaction;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

@Local
public interface TransactionServiceLocal {

    public void rollbackTransaction(final long transactionId);

    public void rollbackPayment(final long paymentId);
    
    public Transaction deposit(final long accountId, final BigDecimal value);

    public Transaction withdraw(final long accountId, final BigDecimal value) throws NotEnoughFundsException;

    public Transaction findById(final long id);

    public List<Transaction> findByAccountId(final long accountId);

    public long requestPayment(final long receiverAccountId, final String description, final BigDecimal value);

    public Transaction approvePayment(long paymentId, final long senderAccountId, final BigDecimal value);

}
