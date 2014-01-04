package com.g5.webservices;

import com.g5.dto.DtoFactory;
import com.g5.dto.TransactionDto;
import com.g5.exceptions.NotEnoughFundsException;
import com.g5.services.PaymentServiceLocal;
import java.math.BigDecimal;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

@WebService(serviceName = "PaymentService")
public class PaymentService {

    @Inject
    private PaymentServiceLocal service;
    @Inject
    private DtoFactory dtoFactory;

    @WebMethod(operationName = "rollbackPayment")
    public void rollbackPayment(@WebParam(name = "paymentId") @XmlElement(required = true) long paymentId) {
        service.rollbackPayment(paymentId);
    }

    @WebMethod(operationName = "requestPayment")
    @XmlElement(name = "paymentId")
    public long requestPayment(@WebParam(name = "receiverAccountId") @XmlElement(required = true) long receiverAccountId, @WebParam(name = "description") String description, @WebParam(name = "value") BigDecimal value) {
        return service.requestPayment(receiverAccountId, description, value);
    }

    @WebMethod(operationName = "approvePayment")
    @XmlElement(name = "transaction")
    public TransactionDto approvePayment(@WebParam(name = "paymentId") @XmlElement(required = true) long paymentId, @WebParam(name = "senderAccountId") long senderAccountId, @WebParam(name = "value") BigDecimal value) throws NotEnoughFundsException {
        return dtoFactory.createTransactionDto(service.approvePayment(paymentId, senderAccountId, value));
    }
}
