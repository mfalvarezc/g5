/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.g5.services.validators;

import com.g5.types.Payment;
import java.math.BigDecimal;
import javax.ejb.Local;

/**
 *
 * @author healarconr
 */
@Local
public interface PaymentValidatorLocal {

    void isNotYetPaid(final Payment payment);

    void hasValue(final Payment payment, final BigDecimal value);

    void exists(final Payment payment);
    
}
