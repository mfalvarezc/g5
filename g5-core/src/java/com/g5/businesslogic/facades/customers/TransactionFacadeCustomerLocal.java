/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.g5.businesslogic.facades.customers;

import com.g5.exceptions.NotEnoughFundsException;
import com.g5.types.Transaction;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juancho
 */
@Local
public interface TransactionFacadeCustomerLocal {
    
    Transaction deposit(final long accountId, final BigDecimal value);
    
    List<Transaction> findMyTransactions(final long accountId);
    
    Transaction withdraw(final long accountId, final BigDecimal value) throws
            NotEnoughFundsException;

}
