package com.g5.webservices;

import com.g5.dto.DtoFactory;
import com.g5.dto.TransactionDto;
import com.g5.exceptions.NotEnoughFundsException;
import com.g5.services.TransactionServiceLocal;
import com.g5.types.Transaction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

@WebService(serviceName = "TransactionService")
public class TransactionService {

    @Inject
    private TransactionServiceLocal service;
    @Inject
    private DtoFactory dtoFactory;

    @WebMethod(operationName = "rollbackTransaction")
    public void rollbackTransaction(@WebParam(name = "transactionId") @XmlElement(required = true) long transactionId) {
        service.rollbackTransaction(transactionId);
    }

    @WebMethod(operationName = "deposit")
    @XmlElement(name = "transaction")
    public TransactionDto deposit(@WebParam(name = "accountId") @XmlElement(required = true) long accountId, @WebParam(name = "value") @XmlElement(required = true) BigDecimal value) {
        return dtoFactory.createTransactionDto(service.deposit(accountId, value));
    }

    @WebMethod(operationName = "withdraw")
    @XmlElement(name = "transaction")
    public TransactionDto withdraw(@WebParam(name = "accountId") @XmlElement(required = true) long accountId, @WebParam(name = "value") @XmlElement(required = true) BigDecimal value) throws NotEnoughFundsException {
        return dtoFactory.createTransactionDto(service.withdraw(accountId, value));
    }

    @WebMethod(operationName = "findTransactionById")
    @XmlElement(name = "transaction")
    public TransactionDto findTransactionById(@WebParam(name = "id") @XmlElement(required = true) long id) {
        return dtoFactory.createTransactionDto(service.findById(id));
    }

    @WebMethod(operationName = "findTransactionsByAccountId")
    @XmlElement(name = "transaction")
    public List<TransactionDto> findTransactionsByAccountId(@WebParam(name = "accountId") @XmlElement(required = true) long accountId) {
        List<Transaction> transactions = service.findByAccountId(accountId);

        List<TransactionDto> transactionDtos = new ArrayList<>(transactions.size());

        for (Transaction transaction : transactions) {
            transactionDtos.add(dtoFactory.createTransactionDto(transaction));
        }

        return transactionDtos;
    }

}
