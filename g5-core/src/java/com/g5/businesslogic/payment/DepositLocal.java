package com.g5.businesslogic.payment;

import com.g5.businesslogic.constraints.Id;
import com.g5.businesslogic.constraints.PositiveDecimal;
import com.g5.types.Transaction;
import java.math.BigDecimal;
import javax.ejb.Local;

@Local
public interface DepositLocal {

    Transaction execute(@Id long accountId, @PositiveDecimal BigDecimal value);

}
