package com.g5.webservices.impl;

import com.g5.webservices.TransactionService;
import com.g5.dto.DtoFactory;
import com.g5.dto.TransactionDto;
import com.g5.exceptions.NotEnoughFundsException;
import com.g5.businesslogic.TransactionFacadeLocal;
import com.g5.types.Transaction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jws.WebService;

@WebService(serviceName = "TransactionService", endpointInterface =
        "com.g5.webservices.TransactionService")
public class TransactionServiceImpl implements TransactionService {

    @EJB
    private TransactionFacadeLocal transactionFacade;
    @Inject
    private DtoFactory dtoFactory;

    @Override
    public void rollbackTransaction(final long transactionId) {
        transactionFacade.rollbackTransaction(transactionId);
    }

    @Override
    public TransactionDto deposit(final long accountId, final BigDecimal value) {
        return dtoFactory.createTransactionDto(transactionFacade.deposit(
                accountId, value));
    }

    @Override
    public TransactionDto withdraw(final long accountId, final BigDecimal value)
            throws NotEnoughFundsException {
        return dtoFactory.createTransactionDto(transactionFacade.withdraw(
                accountId, value));
    }

    @Override
    public TransactionDto findTransactionById(final long id) {
        return dtoFactory.createTransactionDto(transactionFacade.
                findTransactionById(id));
    }

    @Override
    public List<TransactionDto> findTransactionsByAccountId(final long accountId) {
        List<Transaction> transactions = transactionFacade.
                findTransactionsByAccountId(accountId);

        List<TransactionDto> transactionDtos = new ArrayList<>(transactions.
                size());

        for (Transaction transaction : transactions) {
            transactionDtos.add(dtoFactory.createTransactionDto(transaction));
        }

        return transactionDtos;
    }

}
