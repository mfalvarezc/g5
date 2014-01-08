package com.g5.businesslogic;

import com.g5.types.Customer;
import javax.ejb.Local;

@Local
public interface CustomerFacadeLocal {

    void changeCustomerPassword(final long customerId, final String password);

    Customer createCustomer(final String username, final String password);

    void disableCustomer(final long customerId);

    void enableCustomer(final long customerId);

    Customer findCustomerById(final long customerId);

}
