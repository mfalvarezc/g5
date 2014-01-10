package com.g5.webservices.impl;

import com.g5.webservices.PaymentService;
import com.g5.dto.DtoFactory;
import com.g5.dto.TransactionDto;
import com.g5.exceptions.NotEnoughFundsException;
import com.g5.businesslogic.PaymentFacadeLocal;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jws.WebService;

@WebService(serviceName = "PaymentService", endpointInterface =
        "com.g5.webservices.PaymentService")
public class PaymentServiceImpl implements PaymentService {

    @EJB
    private PaymentFacadeLocal paymentFacade;
    @Inject
    private DtoFactory dtoFactory;

    @Override
    public void rollbackPayment(final long paymentId) {
        paymentFacade.rollbackPayment(paymentId);
    }

    @Override
    public long requestPayment(final long receiverAccountId,
            final String description, final BigDecimal value) {
        return paymentFacade.requestPayment(receiverAccountId, description,
                value);
    }

    @Override
    public TransactionDto approvePayment(final long paymentId,
            final long senderAccountId, final BigDecimal value) throws
            NotEnoughFundsException {
        return dtoFactory.createTransactionDto(paymentFacade.approvePayment(
                paymentId, senderAccountId, value));
    }
}
