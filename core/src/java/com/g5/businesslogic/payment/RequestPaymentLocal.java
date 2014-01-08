package com.g5.businesslogic.payment;

import com.g5.businesslogic.constraints.Description;
import com.g5.businesslogic.constraints.Id;
import com.g5.businesslogic.constraints.PositiveDecimal;
import java.math.BigDecimal;
import javax.ejb.Local;

@Local
public interface RequestPaymentLocal {

    long execute(@Id long receiverAccountId, @Description String description,
            @PositiveDecimal BigDecimal value);

}
