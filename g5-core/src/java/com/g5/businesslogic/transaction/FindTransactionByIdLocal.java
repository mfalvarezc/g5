package com.g5.businesslogic.transaction;

import com.g5.businesslogic.constraints.Id;
import com.g5.types.Transaction;
import javax.ejb.Local;

@Local
public interface FindTransactionByIdLocal {

    Transaction execute(@Id long transactionId);

}
