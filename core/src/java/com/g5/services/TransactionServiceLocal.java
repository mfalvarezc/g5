package com.g5.services;

import com.g5.exceptions.NotEnoughFundsException;
import com.g5.types.Transaction;
import com.g5.constraints.Description;
import com.g5.constraints.Id;
import com.g5.constraints.NotZeroDecimal;
import com.g5.constraints.PositiveDecimal;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;
import javax.validation.constraints.NotNull;

@Local
public interface TransactionServiceLocal {

    public Transaction create(@Id final long accountId, @Description String description, @NotZeroDecimal final BigDecimal value);

    public void rollbackTransaction(@Id final long transactionId);

    @NotNull
    public Transaction deposit(@Id final long accountId, @PositiveDecimal final BigDecimal value);

    @NotNull
    public Transaction withdraw(@Id final long accountId, @PositiveDecimal final BigDecimal value) throws NotEnoughFundsException;

    public Transaction findById(@Id final long id);

    @NotNull
    public List<Transaction> findByAccountId(@Id final long accountId);

}
