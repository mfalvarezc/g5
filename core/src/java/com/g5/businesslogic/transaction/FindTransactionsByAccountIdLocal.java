package com.g5.businesslogic.transaction;

import com.g5.businesslogic.constraints.Id;
import com.g5.types.Transaction;
import java.util.List;
import javax.ejb.Local;

@Local
public interface FindTransactionsByAccountIdLocal {

    List<Transaction> execute(@Id long accountId);

}
