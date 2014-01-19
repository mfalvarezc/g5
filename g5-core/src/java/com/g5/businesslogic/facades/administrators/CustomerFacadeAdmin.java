/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.g5.businesslogic.facades.administrators;

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
public class CustomerFacadeAdmin implements CustomerFacadeAdminLocal {

    @Inject
    private Provider<CreateCustomerLocal> createCustomerProvider;
    @Inject
    private Provider<DisableCustomerLocal> disableCustomerProvider;
    @Inject
    private Provider<EnableCustomerLocal> enableCustomerProvider;
    @Inject
    private Provider<FindCustomerByIdLocal> findCustomerByIdProvider;

    @Override
    public Customer createCustomer(String username, String password) {
        return createCustomerProvider.get().execute(username, password);
    }

    @Override
    public void disableCustomer(long customerId) {
        disableCustomerProvider.get().execute(customerId);
    }

    @Override
    public void enableCustomer(long customerId) {
        enableCustomerProvider.get().execute(customerId);
    }

    @Override
    public Customer findCustomerById(long customerId) {
        return findCustomerByIdProvider.get().execute(customerId);
    }
}
