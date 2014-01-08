package com.g5.businesslogic.payment;

import com.g5.businesslogic.constraints.Id;
import javax.ejb.Local;

@Local
public interface RollbackPaymentLocal {

    void execute(@Id long paymentId);

}
