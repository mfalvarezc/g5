package com.g5.webserviceclient;

import com.g5.webserviceclient.TestHelper;
import com.g5.webserviceclient.account.Account;
import com.g5.webserviceclient.account.AccountService;
import com.g5.webserviceclient.account.AccountService_Service;
import com.g5.webserviceclient.customer.CustomerService;
import com.g5.webserviceclient.customer.CustomerService_Service;
import com.g5.webserviceclient.transaction.NotEnoughFundsException_Exception;
import com.g5.webserviceclient.transaction.Transaction;
import com.g5.webserviceclient.transaction.TransactionService;
import com.g5.webserviceclient.transaction.TransactionService_Service;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TransactionServiceTest {

    private AccountService accountService;
    private TransactionService transactionService;
    private long accountId;

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
        accountId = accountService.createAccount(customerId).getId();

        transactionService = (new TransactionService_Service()).
                getTransactionServiceImplPort();
    }

    @Test
    public void testService() throws NotEnoughFundsException_Exception {
        BigDecimal value = new BigDecimal(100);
        BigDecimal newBalance = value;

        Transaction transaction = transactionService.deposit(accountId,
                value);

        assertNotNull(transaction);
        assertTrue(transaction.getId() > 0);
        assertEquals(accountId, transaction.getAccountId());
        // TODO: assert transaction.getDate()
        assertEquals("Deposit", transaction.getDescription());
        assertEquals(0, value.compareTo(transaction.getValue()));

        Transaction transactionFound = transactionService.findTransactionById(
                transaction.getId());

        assertNotNull(transactionFound);
        assertEquals(transaction.getId(), transactionFound.getId());
        assertEquals(transaction.getAccountId(), transactionFound.getAccountId());
        assertEquals(transaction.getDescription(), transactionFound.
                getDescription());
        assertEquals(0, transaction.getValue().compareTo(transactionFound.
                getValue()));

        Account accountFound = accountService.findAccountById(accountId);

        assertEquals(0, newBalance.compareTo(accountFound.getBalance()));

        value = new BigDecimal(50);
        newBalance = newBalance.subtract(value);

        transaction = transactionService.withdraw(accountId,
                value);

        assertNotNull(transaction);
        assertTrue(transaction.getId() > 0);
        assertEquals(accountId, transaction.getAccountId());
        // TODO: assert transaction.getDate()
        assertEquals("Withdraw", transaction.getDescription());
        assertEquals(0, value.negate().compareTo(transaction.getValue()));

        transactionFound = transactionService.findTransactionById(
                transaction.getId());

        assertNotNull(transactionFound);
        assertEquals(transaction.getId(), transactionFound.getId());
        assertEquals(transaction.getAccountId(), transactionFound.getAccountId());
        assertEquals(transaction.getDescription(), transactionFound.
                getDescription());
        assertEquals(0, transaction.getValue().compareTo(transactionFound.
                getValue()));

        accountFound = accountService.findAccountById(accountId);

        assertEquals(0, newBalance.compareTo(accountFound.getBalance()));

        List<Transaction> transactionsFound = transactionService.
                findTransactionsByAccountId(accountId);

        assertNotNull(transactionsFound);
        assertEquals(2, transactionsFound.size());

        newBalance = newBalance.add(value);

        transactionService.rollbackTransaction(transaction.getId());

        accountFound = accountService.findAccountById(accountId);

        assertEquals(0, newBalance.compareTo(accountFound.getBalance()));

        transactionsFound = transactionService.
                findTransactionsByAccountId(accountId);

        assertNotNull(transactionsFound);
        assertEquals(3, transactionsFound.size());

        value = new BigDecimal(1000000);

        try {
            transactionService.withdraw(accountId, value);
            fail("NotEnoughFundException was not thrown");
        } catch (NotEnoughFundsException_Exception ex) {
        }
    }

}
