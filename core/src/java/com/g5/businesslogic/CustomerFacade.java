package com.g5.businesslogic;

import com.g5.businesslogic.customer.ChangeCustomerPasswordLocal;
import com.g5.businesslogic.customer.CreateCustomerLocal;
import com.g5.businesslogic.customer.DisableCustomerLocal;
import com.g5.businesslogic.customer.EnableCustomerLocal;
import com.g5.businesslogic.customer.FindCustomerByIdLocal;
import com.g5.types.Customer;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Provider;

@Stateless
public class CustomerFacade implements CustomerFacadeLocal {

    @Inject
    private Provider<ChangeCustomerPasswordLocal> changeCustomerPasswordProvider;
    @Inject
    private Provider<CreateCustomerLocal> createCustomerProvider;
    @Inject
    private Provider<DisableCustomerLocal> disableCustomerProvider;
    @Inject
    private Provider<EnableCustomerLocal> enableCustomerProvider;
    @Inject
    private Provider<FindCustomerByIdLocal> findCustomerByIdProvider;

    @Override
    public void changeCustomerPassword(long customerId, String password) {
        changeCustomerPasswordProvider.get().execute(customerId, password);
    }

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
