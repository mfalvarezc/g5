/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.g5.businesslogic.transaction;

import com.g5.businesslogic.account.AccountValidatorLocal;
import com.g5.businesslogic.constraints.Id;
import com.g5.entities.EntityClassHelperLocal;
import com.g5.types.Account;
import com.g5.types.Transaction;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Juancho
 */
@Stateless
public class FindMyTransactions implements FindMyTransactionsLocal {

    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;
    @Inject
    private EntityClassHelperLocal entityClassHelper;
    @Inject
    private AccountValidatorLocal accountValidator;

    @Override
    @RolesAllowed({"Customers"})
    public List<Transaction> execute(@Id long accountId) {
        Account account = entityManager.
                find(entityClassHelper.getAccountClass(), accountId);

        accountValidator.exists(account);

        return (List<Transaction>) (List<?>) entityManager.createNamedQuery(
                "Transaction.findByAccount", entityClassHelper.
                getTransactionClass()).setParameter("account", account).
                getResultList();
    }
}
