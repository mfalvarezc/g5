package com.g5.webservices;

import com.g5.services.CustomerServiceLocal;
import com.g5.dto.CustomerDto;
import com.g5.dto.DtoFactory;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

@WebService(serviceName = "CustomerService")
public class CustomerService {

    @Inject
    private CustomerServiceLocal customerService;
    @Inject
    private DtoFactory dtoFactory;

    @WebMethod(operationName = "findCustomerById")
    @XmlElement(name = "customer")
    public CustomerDto findCustomerById(@WebParam(name = "customerId") @XmlElement(required = true) long customerId) {
        return dtoFactory.createCustomerDto(customerService.findById(customerId));
    }

    @WebMethod(operationName = "createCustomer")
    @XmlElement(name = "customer")
    public CustomerDto createCustomer(@WebParam(name = "username") @XmlElement(required = true) String username, @WebParam(name = "password") @XmlElement(required = true) String password) {
        return dtoFactory.createCustomerDto(customerService.create(username, password));
    }

    @WebMethod(operationName = "changeCustomerPassword")
    public void changeCustomerPassword(@WebParam(name = "id") @XmlElement(required = true) long id, @WebParam(name = "password") @XmlElement(required = true) String password) {
        customerService.changePassword(id, password);
    }

    @WebMethod(operationName = "disableCustomer")
    public void disableCustomer(@WebParam(name = "id") @XmlElement(required = true) long id) {
        customerService.disable(id);
    }

    @WebMethod(operationName = "enableCustomer")
    public void enableCustomer(@WebParam(name = "id") @XmlElement(required = true) long id) {
        customerService.enable(id);
    }

}
