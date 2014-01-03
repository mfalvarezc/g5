package com.g5.dao;

import com.g5.entities.PaymentEntity;
import com.g5.entities.TransactionEntity;
import com.g5.types.Payment;
import com.g5.types.Transaction;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PaymentDao extends AbstractBaseDao<Payment, PaymentEntity> implements PaymentDaoRemote, PaymentDaoLocal {

    @Inject
    private TransactionDaoLocal transactionDao;

    @Override
    public Payment findByTransaction(Transaction transaction) {
        List<PaymentEntity> result = entityManager.createNamedQuery("Payment.findByTransaction", PaymentEntity.class).setParameter("transaction", transaction).getResultList();

        if (result.isEmpty()) {
            return null;
        } else if (result.size() == 1) {
            return result.get(0);
        } else {
            throw new RuntimeException("The transaction is in more than one payment");
        }
    }

}
