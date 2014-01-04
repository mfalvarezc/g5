package com.g5.webservices;

import com.g5.dto.AccountDto;
import com.g5.dto.DtoFactory;
import com.g5.services.AccountServiceLocal;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

@WebService(serviceName = "AccountService")
public class AccountService {

    @Inject
    private AccountServiceLocal service;
    @Inject
    private DtoFactory dtoFactory;

    @WebMethod(operationName = "createAccount")
    @XmlElement(name = "account")
    public AccountDto createAccount(@WebParam(name = "customerId") @XmlElement(required = true) long customerId) {
        return dtoFactory.createAccountDto(service.create(customerId));
    }

    @WebMethod(operationName = "findAccountById")
    @XmlElement(name = "account")
    public AccountDto findAccountById(@WebParam(name = "id") @XmlElement(required = true) long id) {
        return dtoFactory.createAccountDto(service.findById(id));
    }

    @WebMethod(operationName = "closeAccount")
    public void closeAccount(@WebParam(name = "id") @XmlElement(required = true) long id) {
        service.close(id);
    }

    @WebMethod(operationName = "reopenAccount")
    public void reopenAccount(@WebParam(name = "id") @XmlElement(required = true) long id) {
        service.reopen(id);
    }

}
