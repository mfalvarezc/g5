package com.g5.businesslogic.customer;

import com.g5.businesslogic.constraints.Id;
import com.g5.businesslogic.constraints.Password;
import javax.ejb.Local;

@Local
public interface ChangeCustomerPasswordLocal {

    void execute(@Id long customerId, @Password String password);

}
