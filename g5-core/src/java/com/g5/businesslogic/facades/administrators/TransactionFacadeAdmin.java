/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.g5.businesslogic.facades.administrators;

import com.g5.businesslogic.payment.DepositLocal;
import com.g5.businesslogic.payment.WithdrawLocal;
import com.g5.businesslogic.transaction.FindTransactionByIdLocal;
import com.g5.businesslogic.transaction.FindTransactionsByAccountIdLocal;
import com.g5.businesslogic.transaction.RollbackTransactionLocal;
import com.g5.types.Transaction;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Provider;

/**
 *
 * @author Juancho
 */
@Stateless
public class TransactionFacadeAdmin implements TransactionFacadeAdminLocal {

    @Inject
    private Provider<FindTransactionByIdLocal> findTransactionByIdProvider;
    @Inject
    private Provider<FindTransactionsByAccountIdLocal> findTransactionsByAccountIdProvider;
    @Inject
    private Provider<RollbackTransactionLocal> rollbackTransactionProvider;
    
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
}
