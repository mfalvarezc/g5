/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.g5.businesslogic.facades.administrators;

import com.g5.types.Transaction;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juancho
 */
@Local
public interface TransactionFacadeAdminLocal {
    
     Transaction findTransactionById(final long transactionId);

    List<Transaction> findTransactionsByAccountId(final long accountId);

    void rollbackTransaction(final long transactionId);
}
