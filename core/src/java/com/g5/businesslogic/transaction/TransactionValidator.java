package com.g5.businesslogic.transaction;

import com.g5.entities.EntityClassHelperLocal;
import com.g5.types.Payment;
import com.g5.types.Transaction;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TransactionValidator implements TransactionValidatorLocal {

    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;
    @Inject
    private EntityClassHelperLocal entityClassHelper;

    @Override
    public void exists(final Transaction transaction) {
        if (transaction == null || transaction.getId() <= 0) {
            throw new IllegalArgumentException(
                    "The transaction does not exists.");
        }
    }

    @Override
    public void doesNotBelongToPayment(final Transaction transaction) {
        List<? extends Payment> payments = entityManager.createNamedQuery(
                "Payment.findByTransaction", entityClassHelper.getPaymentClass()).
                setParameter("transaction", transaction).getResultList();

        if (!payments.isEmpty()) {
            throw new UnsupportedOperationException(
                    "The transaction belongs to a payment. The rollback must be performed on the payment.");
        }
    }

}
