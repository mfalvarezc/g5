/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.g5.businesslogic.account;

import com.g5.businesslogic.constraints.Id;
import com.g5.types.Account;
import javax.ejb.Local;

@Local
public interface FindMyAccountsLocal {
    
    Account execute(@Id long accountId);
}
