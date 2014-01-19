/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.g5.businesslogic.facades.administrators;

import com.g5.types.Account;
import javax.ejb.Local;

/**
 *
 * @author Juancho
 */
@Local
public interface AccountFacadeAdminLocal {
 
    void closeAccount(long accountId);

    Account createAccount(long customerId);

    Account findAccountById(long accountId);

    void reopenAccount(long accountId);

}
