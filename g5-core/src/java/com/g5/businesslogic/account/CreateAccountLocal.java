package com.g5.businesslogic.account;

import com.g5.businesslogic.constraints.Id;
import com.g5.types.Account;
import javax.ejb.Local;

@Local
public interface CreateAccountLocal {

    Account execute(@Id long customerId);

}
