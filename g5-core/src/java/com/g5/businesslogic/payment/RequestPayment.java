package com.g5.businesslogic.payment;

import com.g5.entities.EntityClassHelperLocal;
import com.g5.entities.EntityFactoryLocal;
import com.g5.businesslogic.account.AccountValidatorLocal;
import com.g5.businesslogic.constraints.Description;
import com.g5.businesslogic.constraints.Id;
import com.g5.businesslogic.constraints.PositiveDecimal;
import com.g5.types.Account;
import com.g5.types.Payment;
import java.math.BigDecimal;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class RequestPayment implements RequestPaymentLocal {

    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;
    @Inject
    private EntityClassHelperLocal entityClassHelper;
    @Inject
    private AccountValidatorLocal accountValidator;
    @Inject
    private EntityFactoryLocal entityFactory;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @RolesAllowed({"Customers"})
    public long execute(@Id long receiverAccountId,
            @Description String description, @PositiveDecimal BigDecimal value) {
        Account receiverAccount = entityManager.find(entityClassHelper.
                getAccountClass(), receiverAccountId);

        accountValidator.exists(receiverAccount);
        accountValidator.isOpen(receiverAccount);

        Payment payment = entityFactory.createPayment();
        payment.setReceiverAccount(receiverAccount);
        payment.setDescription(description);
        payment.setValue(value);

        entityManager.persist(payment);

        return payment.getId();
    }

}
