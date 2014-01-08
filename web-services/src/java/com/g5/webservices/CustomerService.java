package com.g5.webservices;

import com.g5.dto.CustomerDto;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

@WebService(name = "CustomerService")
public interface CustomerService {

    @WebMethod(operationName = "changeCustomerPassword")
    public void changeCustomerPassword(@WebParam(name = "customerId")
            @XmlElement(required = true) final long customerId, @WebParam(name =
                    "password")
            @XmlElement(required = true) final String password);

    @WebMethod(operationName = "createCustomer")
    @WebResult(name = "customer")
    public CustomerDto createCustomer(@WebParam(name = "username")
            @XmlElement(required = true) final String username, @WebParam(name =
                    "password")
            @XmlElement(required = true) final String password);

    @WebMethod(operationName = "disableCustomer")
    public void disableCustomer(@WebParam(name = "customerId")
            @XmlElement(required = true) final long customerId);

    @WebMethod(operationName = "enableCustomer")
    public void enableCustomer(@WebParam(name = "customerId")
            @XmlElement(required = true) final long customerId);

    @WebMethod(operationName = "findCustomerById")
    @WebResult(name = "customer")
    public CustomerDto findCustomerById(@WebParam(name = "customerId")
            @XmlElement(required = true) final long customerId);

}
