package com.g5.businesslogic.transaction;

import com.g5.businesslogic.constraints.Id;
import javax.ejb.Local;

@Local
public interface RollbackTransactionLocal {

    void execute(@Id long transactionId);

}
