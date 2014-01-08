package com.g5.businesslogic.customer;

import com.g5.businesslogic.constraints.Id;
import com.g5.types.Customer;
import javax.ejb.Local;

@Local
public interface FindCustomerByIdLocal {

    Customer execute(@Id long customerId);

}
