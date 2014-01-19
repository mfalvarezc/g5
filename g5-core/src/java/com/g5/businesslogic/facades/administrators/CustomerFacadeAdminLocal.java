/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.g5.businesslogic.facades.administrators;

import com.g5.types.Customer;
import javax.ejb.Local;

/**
 *
 * @author Juancho
 */
@Local
public interface CustomerFacadeAdminLocal {
    
    Customer createCustomer(final String username, final String password);

    void disableCustomer(final long customerId);

    void enableCustomer(final long customerId);

    Customer findCustomerById(final long customerId);
    
}
