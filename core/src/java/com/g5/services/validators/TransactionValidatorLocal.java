/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.g5.services.validators;

import com.g5.types.Transaction;
import javax.ejb.Local;

/**
 *
 * @author healarconr
 */
@Local
public interface TransactionValidatorLocal {

    void exists(final Transaction transaction);

    void doesNotBelongToPayment(final Transaction transaction);
    
}
