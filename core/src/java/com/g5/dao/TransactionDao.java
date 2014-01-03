package com.g5.dao;

import com.g5.types.Account;
import com.g5.types.Transaction;
import com.g5.entities.TransactionEntity;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class TransactionDao extends AbstractBaseDao<Transaction, TransactionEntity> implements TransactionDaoRemote, TransactionDaoLocal {

    @Override
    public List<Transaction> findByAccount(final Account account) {
        return (List<Transaction>) (List<?>) entityManager.createNamedQuery("Transaction.findByAccount", TransactionEntity.class).setParameter("account", account).getResultList();
    }

    @Override
    public void removeByAccount(Account account) {
        entityManager.createNamedQuery("Transaction.removeByAccount").setParameter("account", account).executeUpdate();
    }

}
