package com.g5.businesslogic;

import com.g5.exceptions.NotEnoughFundsException;
import com.g5.types.Transaction;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

@Local
public interface TransactionFacadeLocal {

    Transaction deposit(final long accountId, final BigDecimal value);

    Transaction findTransactionById(final long transactionId);

    List<Transaction> findTransactionsByAccountId(final long accountId);

    void rollbackTransaction(final long transactionId);

    Transaction withdraw(final long accountId, final BigDecimal value) throws
            NotEnoughFundsException;

}
