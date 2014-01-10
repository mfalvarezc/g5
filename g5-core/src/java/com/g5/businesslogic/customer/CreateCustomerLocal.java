package com.g5.businesslogic.customer;

import com.g5.businesslogic.constraints.Password;
import com.g5.businesslogic.constraints.Username;
import com.g5.types.Customer;
import javax.ejb.Local;

@Local
public interface CreateCustomerLocal {

    Customer execute(@Username String username, @Password String password);

}
