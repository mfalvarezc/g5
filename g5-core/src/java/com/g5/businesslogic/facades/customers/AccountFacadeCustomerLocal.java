/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.g5.businesslogic.facades.customers;

import javax.ejb.Local;

/**
 *
 * @author Juancho
 */
@Local
public interface AccountFacadeCustomerLocal {
    
    void changeCustomerPassword(final long customerId, final String password);
    
}
