package com.g5.webservices;

import com.g5.dto.TransactionDto;
import com.g5.exceptions.NotEnoughFundsException;
import java.math.BigDecimal;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

@WebService(name = "TransactionService")
public interface TransactionService {

    @WebMethod(operationName = "deposit")
    @WebResult(name = "transaction")
    public TransactionDto deposit(@WebParam(name = "accountId")
            @XmlElement(required = true) final long accountId, @WebParam(name =
                    "value")
            @XmlElement(required = true) final BigDecimal value);

    @WebMethod(operationName = "findTransactionById")
    @WebResult(name = "transaction")
    public TransactionDto findTransactionById(@WebParam(name = "transactionId")
            @XmlElement(required = true) final long transactionId);

    @WebMethod(operationName = "findTransactionsByAccountId")
    @WebResult(name = "transaction")
    public List<TransactionDto> findTransactionsByAccountId(@WebParam(name =
            "accountId")
            @XmlElement(required = true) final long accountId);

    @WebMethod(operationName = "rollbackTransaction")
    public void rollbackTransaction(@WebParam(name = "transactionId")
            @XmlElement(required = true) final long transactionId);

    @WebMethod(operationName = "withdraw")
    @WebResult(name = "transaction")
    public TransactionDto withdraw(@WebParam(name = "accountId")
            @XmlElement(required = true) final long accountId, @WebParam(name =
                    "value")
            @XmlElement(required = true) final BigDecimal value) throws
            NotEnoughFundsException;

}
