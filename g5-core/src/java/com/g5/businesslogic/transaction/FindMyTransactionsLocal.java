/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.g5.businesslogic.transaction;

import com.g5.businesslogic.constraints.Id;
import com.g5.types.Transaction;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juancho
 */
@Local
public interface FindMyTransactionsLocal {
    
    List<Transaction> execute(@Id long accountId);

}
