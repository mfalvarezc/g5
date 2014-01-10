package com.g5.businesslogic.customer;

import com.g5.types.Customer;
import javax.ejb.Stateless;

@Stateless
public class CustomerValidator implements CustomerValidatorLocal {

    @Override
    public void isEnabled(final Customer customer) {
        if (!customer.isEnabled()) {
            throw new IllegalStateException("The customer is disabled.");
        }
    }

    @Override
    public void isDisabled(final Customer customer) {
        if (customer.isEnabled()) {
            throw new IllegalStateException("The customer is enabled.");
        }
    }

    @Override
    public void exists(final Customer customer) {
        if (customer == null || customer.getId() <= 0) {
            throw new IllegalArgumentException("The customer does not exist.");
        }
    }

}
