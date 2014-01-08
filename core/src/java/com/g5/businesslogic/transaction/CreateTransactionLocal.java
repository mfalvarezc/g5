package com.g5.businesslogic.transaction;

import com.g5.businesslogic.constraints.Description;
import com.g5.businesslogic.constraints.Id;
import com.g5.businesslogic.constraints.NotZeroDecimal;
import com.g5.types.Transaction;
import java.math.BigDecimal;
import javax.ejb.Local;

@Local
public interface CreateTransactionLocal {

    Transaction execute(@Id long accountId, @Description String description,
            @NotZeroDecimal BigDecimal value);

}
