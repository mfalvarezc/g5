/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.g5.businesslogic.facades.administrators;

import com.g5.exceptions.NotEnoughFundsException;
import com.g5.types.Transaction;
import java.math.BigDecimal;
import javax.ejb.Local;

/**
 *
 * @author Juancho
 */
@Local
public interface PaymentFacadeAdminLocal {
    
    Transaction approvePayment(long paymentId, final long senderAccountId,
            final BigDecimal value) throws NotEnoughFundsException;

    long requestPayment(final long receiverAccountId, final String description,
            final BigDecimal value);

    void rollbackPayment(final long paymentId);

}
