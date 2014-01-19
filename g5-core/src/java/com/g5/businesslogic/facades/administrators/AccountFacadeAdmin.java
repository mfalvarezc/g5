/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.g5.businesslogic.facades.administrators;

import com.g5.businesslogic.account.CloseAccountLocal;
import com.g5.businesslogic.account.CreateAccountLocal;
import com.g5.businesslogic.account.FindAccountByIdLocal;
import com.g5.businesslogic.account.ReopenAccountLocal;
import com.g5.types.Account;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Provider;

/**
 *
 * @author Juancho
 */
@Stateless
public class AccountFacadeAdmin implements AccountFacadeAdminLocal {

    @Inject
    private Provider<CreateAccountLocal> createAccountProvider;
    @Inject
    private Provider<FindAccountByIdLocal> findAccountByIdProvider;
    @Inject
    private Provider<CloseAccountLocal> closeAccountProvider;
    @Inject
    private Provider<ReopenAccountLocal> reopenAccountProvider;

    @Override
    public Account createAccount(long customerId) {
        return createAccountProvider.get().execute(customerId);
    }

    @Override
    public Account findAccountById(long accountId) {
        return findAccountByIdProvider.get().execute(accountId);
    }

    @Override
    public void closeAccount(long accountId) {
        closeAccountProvider.get().execute(accountId);
    }

    @Override
    public void reopenAccount(long accountId) {
        reopenAccountProvider.get().execute(accountId);
    }
}
