package com.g5.businesslogic;

import com.g5.businesslogic.payment.DepositLocal;
import com.g5.businesslogic.payment.WithdrawLocal;
import com.g5.businesslogic.transaction.FindTransactionByIdLocal;
import com.g5.businesslogic.transaction.FindTransactionsByAccountIdLocal;
import com.g5.businesslogic.transaction.RollbackTransactionLocal;
import com.g5.exceptions.NotEnoughFundsException;
import com.g5.types.Transaction;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Provider;

@Stateless
public class TransactionFacade implements TransactionFacadeLocal {

    @Inject
    private Provider<DepositLocal> depositProvider;
    @Inject
    private Provider<FindTransactionByIdLocal> findTransactionByIdProvider;
    @Inject
    private Provider<FindTransactionsByAccountIdLocal> findTransactionsByAccountIdProvider;
    @Inject
    private Provider<RollbackTransactionLocal> rollbackTransactionProvider;
    @Inject
    private Provider<WithdrawLocal> withdrawProvider;

    @Override
    public Transaction deposit(long accountId, BigDecimal value) {
        return depositProvider.get().execute(accountId, value);
    }

    @Override
    public Transaction findTransactionById(long transactionId) {
        return findTransactionByIdProvider.get().execute(transactionId);
    }

    @Override
    public List<Transaction> findTransactionsByAccountId(long accountId) {
        return findTransactionsByAccountIdProvider.get().execute(accountId);
    }

    @Override
    public void rollbackTransaction(long transactionId) {
        rollbackTransactionProvider.get().execute(transactionId);
    }

    @Override
    public Transaction withdraw(long accountId, BigDecimal value) throws
            NotEnoughFundsException {
        return withdrawProvider.get().execute(accountId, value);
    }

}
