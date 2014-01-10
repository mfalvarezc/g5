package com.g5.webservices.impl;

import com.g5.webservices.AccountService;
import com.g5.dto.AccountDto;
import com.g5.dto.DtoFactory;
import com.g5.businesslogic.AccountFacadeLocal;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jws.WebService;

@WebService(serviceName = "AccountService", endpointInterface =
        "com.g5.webservices.AccountService")
public class AccountServiceImpl implements AccountService {

    @EJB
    private AccountFacadeLocal accountFacade;
    @Inject
    private DtoFactory dtoFactory;

    @Override
    public AccountDto createAccount(final long customerId) {
        return dtoFactory.createAccountDto(accountFacade.createAccount(
                customerId));
    }

    @Override
    public AccountDto findAccountById(final long accountId) {
        return dtoFactory.createAccountDto(accountFacade.findAccountById(
                accountId));
    }

    @Override
    public void closeAccount(final long accountId) {
        accountFacade.closeAccount(accountId);
    }

    @Override
    public void reopenAccount(final long accountId) {
        accountFacade.reopenAccount(accountId);
    }

}
