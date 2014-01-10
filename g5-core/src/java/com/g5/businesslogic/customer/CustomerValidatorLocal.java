package com.g5.businesslogic.customer;

import com.g5.types.Customer;
import javax.ejb.Local;

@Local
public interface CustomerValidatorLocal {

    public void isEnabled(final Customer customer);

    public void isDisabled(final Customer customer);

    void exists(final Customer customer);

}
