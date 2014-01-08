package com.g5.webservices;

import com.g5.dto.AccountDto;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

@WebService(name = "AccountService")
public interface AccountService {

    @WebMethod(operationName = "closeAccount")
    void closeAccount(@WebParam(name = "accountId")
            @XmlElement(required = true) final long accountId);

    @WebMethod(operationName = "createAccount")
    @WebResult(name = "account")
    public AccountDto createAccount(@WebParam(name = "customerId")
            @XmlElement(required = true) final long customerId);

    @WebMethod(operationName = "findAccountById")
    @WebResult(name = "account")
    public AccountDto findAccountById(@WebParam(name = "accountId")
            @XmlElement(required = true) final long accountId);

    @WebMethod(operationName = "reopenAccount")
    public void reopenAccount(@WebParam(name = "accountId")
            @XmlElement(required = true) final long accountId);

}
