/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.g5.businesslogic.facades.customers;

import com.g5.businesslogic.payment.DepositLocal;
import com.g5.businesslogic.payment.WithdrawLocal;
import com.g5.businesslogic.transaction.FindMyTransactions;
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

/**
 *
 * @author Juancho
 */
@Stateless
public class TransactionFacadeCustomer implements TransactionFacadeCustomerLocal {

    @Inject
    private Provider<DepositLocal> depositProvider;
    @Inject
    private Provider<FindMyTransactions> findMyTransactionsProvider;
    @Inject
    private Provider<WithdrawLocal> withdrawProvider;
    
    @Override
    public Transaction deposit(long accountId, BigDecimal value) {
        return depositProvider.get().execute(accountId, value);
    }
    
    @Override
    public List<Transaction> findMyTransactions(long transactionId) {
        return findMyTransactionsProvider.get().execute(transactionId);
    }
    
    @Override
    public Transaction withdraw(long accountId, BigDecimal value) throws
            NotEnoughFundsException {
        return withdrawProvider.get().execute(accountId, value);
    }
    
    
}
