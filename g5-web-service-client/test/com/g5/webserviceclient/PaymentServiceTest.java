package com.g5.webserviceclient;

import com.g5.webserviceclient.account.Account;
import com.g5.webserviceclient.account.AccountService;
import com.g5.webserviceclient.account.AccountService_Service;
import com.g5.webserviceclient.customer.CustomerService;
import com.g5.webserviceclient.customer.CustomerService_Service;
import com.g5.webserviceclient.payment.PaymentService;
import com.g5.webserviceclient.payment.PaymentService_Service;
import com.g5.webserviceclient.payment.NotEnoughFundsException_Exception;
import com.g5.webserviceclient.transaction.Transaction;
import com.g5.webserviceclient.transaction.TransactionService;
import com.g5.webserviceclient.transaction.TransactionService_Service;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PaymentServiceTest {

    private AccountService accountService;
    private TransactionService transactionService;
    private PaymentService paymentService;
    private long receiverAccountId;
    private long senderAccountId;

    @Before
    public void setUp() {
        TestHelper.configureTrustStore();

        CustomerService customerService = (new CustomerService_Service()).
                getCustomerServiceImplPort();
        long customerId = customerService.
                createCustomer(TestHelper.getRandomUsername(), "Pa$$w0rd").
                getId();

        accountService = (new AccountService_Service()).
                getAccountServiceImplPort();
        receiverAccountId = accountService.createAccount(customerId).getId();
        senderAccountId = accountService.createAccount(customerId).getId();

        transactionService = (new TransactionService_Service()).
                getTransactionServiceImplPort();

        transactionService.deposit(senderAccountId, new BigDecimal(100));

        paymentService = (new PaymentService_Service()).
                getPaymentServiceImplPort();
    }

    @Test
    public void testService() throws NotEnoughFundsException_Exception {
        String description = "Payment description";

        BigDecimal value = new BigDecimal(50);
        BigDecimal newReceiverBalance = value;
        BigDecimal newSenderBalance = value;

        long paymentId = paymentService.requestPayment(receiverAccountId,
                description,
                value);

        com.g5.webserviceclient.payment.Transaction transaction =
                paymentService.approvePayment(paymentId,
                        senderAccountId, value);

        Account accountFound = accountService.findAccountById(receiverAccountId);

        assertEquals(0, newReceiverBalance.compareTo(accountFound.getBalance()));

        List<Transaction> transactions = transactionService.
                findTransactionsByAccountId(receiverAccountId);

        assertEquals(1, transactions.size());

        Transaction receiverTransaction = transactions.get(0);

        assertEquals(description, receiverTransaction.getDescription());
        assertEquals(0, value.compareTo(receiverTransaction.getValue()));

        accountFound = accountService.findAccountById(senderAccountId);

        assertEquals(0, newSenderBalance.compareTo(accountFound.getBalance()));

        transactions = transactionService.
                findTransactionsByAccountId(senderAccountId);

        assertEquals(2, transactions.size());

        Transaction senderTransaction = transactions.get(1);

        assertEquals(transaction.getId(), senderTransaction.getId());
        assertEquals(description, senderTransaction.getDescription());
        assertEquals(0, value.negate().compareTo(senderTransaction.getValue()));

        newReceiverBalance = newReceiverBalance.subtract(value);
        newSenderBalance = newSenderBalance.add(value);

        paymentService.rollbackPayment(paymentId);

        accountFound = accountService.findAccountById(receiverAccountId);

        assertEquals(0, newReceiverBalance.compareTo(accountFound.getBalance()));

        transactions = transactionService.
                findTransactionsByAccountId(receiverAccountId);

        assertEquals(2, transactions.size());

        receiverTransaction = transactions.get(1);

        assertEquals("Rollback - " + description, receiverTransaction.
                getDescription());
        assertEquals(0, value.negate().compareTo(receiverTransaction.getValue()));

        accountFound = accountService.findAccountById(senderAccountId);

        assertEquals(0, newSenderBalance.compareTo(accountFound.getBalance()));

        transactions = transactionService.
                findTransactionsByAccountId(senderAccountId);

        assertEquals(3, transactions.size());

        senderTransaction = transactions.get(2);

        assertEquals("Rollback - " + description, senderTransaction.
                getDescription());
        assertEquals(0, value.compareTo(senderTransaction.getValue()));

    }

}
