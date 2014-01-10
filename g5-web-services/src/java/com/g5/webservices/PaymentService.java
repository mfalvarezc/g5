package com.g5.webservices;

import com.g5.dto.TransactionDto;
import com.g5.exceptions.NotEnoughFundsException;
import java.math.BigDecimal;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

@WebService(name = "PaymentService")
public interface PaymentService {

    @WebMethod(operationName = "approvePayment")
    @WebResult(name = "transaction")
    public TransactionDto approvePayment(@WebParam(name = "paymentId")
            @XmlElement(required = true) final long paymentId, @WebParam(name =
                    "senderAccountId") @XmlElement(required = true) final long senderAccountId,
            @WebParam(name = "value") @XmlElement(required = true) final BigDecimal value)
            throws NotEnoughFundsException;

    @WebMethod(operationName = "requestPayment")
    @WebResult(name = "paymentId")
    public long requestPayment(@WebParam(name = "receiverAccountId")
            @XmlElement(required = true) final long receiverAccountId,
            @WebParam(name = "description") @XmlElement(required = true) final String description,
            @WebParam(
                    name = "value") @XmlElement(required = true) final BigDecimal value);

    @WebMethod(operationName = "rollbackPayment")
    public void rollbackPayment(@WebParam(name = "paymentId")
            @XmlElement(required = true) final long paymentId);

}
