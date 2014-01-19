/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.g5.businesslogic.facades.customers;

import com.g5.businesslogic.customer.ChangeCustomerPasswordLocal;
import com.g5.businesslogic.customer.CreateCustomerLocal;
import com.g5.businesslogic.customer.DisableCustomerLocal;
import com.g5.businesslogic.customer.EnableCustomerLocal;
import com.g5.businesslogic.customer.FindCustomerByIdLocal;
import com.g5.types.Customer;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Provider;

/**
 *
 * @author Juancho
 */
@Stateless
public class AccountFacadeCustomer implements AccountFacadeCustomerLocal {

    @Inject
    private Provider<ChangeCustomerPasswordLocal> changeCustomerPasswordProvider;
  
    @Override
    public void changeCustomerPassword(long customerId, String password) {
        changeCustomerPasswordProvider.get().execute(customerId, password);
    }

}
