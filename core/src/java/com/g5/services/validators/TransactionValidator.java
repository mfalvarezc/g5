package com.g5.services.validators;

import com.g5.dao.PaymentDaoLocal;
import com.g5.types.Payment;
import com.g5.types.Transaction;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TransactionValidator implements TransactionValidatorLocal {

    @Inject
    private PaymentDaoLocal paymentDao;
    
    @Override
    public void exists(final Transaction transaction) {
        if (transaction == null || transaction.getId() <= 0) {
            throw new IllegalArgumentException("The transaction does not exists.");
        }
    }

    @Override
    public void doesNotBelongToPayment(final Transaction transaction) {
        Payment payment = paymentDao.findByTransaction(transaction);

        if (payment != null) {
            throw new UnsupportedOperationException("The transaction belongs to a payment. The rollback must be performed on the payment.");
        }
    }

}
