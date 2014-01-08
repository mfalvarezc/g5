package com.g5.webservices.impl;

import com.g5.webservices.CustomerService;
import com.g5.businesslogic.CustomerFacadeLocal;
import com.g5.dto.CustomerDto;
import com.g5.dto.DtoFactory;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jws.WebService;

@WebService(serviceName = "CustomerService", endpointInterface =
        "com.g5.webservices.CustomerService")
public class CustomerServiceImpl implements CustomerService {

    @EJB
    private CustomerFacadeLocal customerFacade;
    @Inject
    private DtoFactory dtoFactory;

    @Override
    public CustomerDto findCustomerById(final long customerId) {
        return dtoFactory.createCustomerDto(customerFacade.findCustomerById(
                customerId));
    }

    @Override
    public CustomerDto createCustomer(final String username,
            final String password) {
        return dtoFactory.createCustomerDto(customerFacade.createCustomer(
                username, password));
    }

    @Override
    public void changeCustomerPassword(final long customerId,
            final String password) {
        customerFacade.changeCustomerPassword(customerId, password);
    }

    @Override
    public void disableCustomer(final long customerId) {
        customerFacade.disableCustomer(customerId);
    }

    @Override
    public void enableCustomer(final long id) {
        customerFacade.enableCustomer(id);
    }
}
