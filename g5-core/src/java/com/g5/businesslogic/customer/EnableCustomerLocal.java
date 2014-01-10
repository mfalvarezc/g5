package com.g5.businesslogic.customer;

import com.g5.businesslogic.constraints.Id;
import javax.ejb.Local;

@Local
public interface EnableCustomerLocal {

    void execute(@Id long customerId);

}
