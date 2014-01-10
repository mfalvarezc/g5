package com.g5.webserviceclient;

import com.g5.webserviceclient.account.Account;
import com.g5.webserviceclient.account.AccountService;
import com.g5.webserviceclient.account.AccountService_Service;
import com.g5.webserviceclient.customer.Customer;
import com.g5.webserviceclient.customer.CustomerService;
import com.g5.webserviceclient.customer.CustomerService_Service;
import java.math.BigDecimal;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class AccountServiceTest {

    private AccountService accountService;
    private Customer customer;

    @Before
    public void setUp() {
        TestHelper.configureTrustStore();
        accountService = (new AccountService_Service()).
                getAccountServiceImplPort();

        CustomerService customerService = (new CustomerService_Service()).
                getCustomerServiceImplPort();
        customer = customerService.
                createCustomer(TestHelper.getRandomUsername(), "Pa$$w0rd");
    }

    @Test
    public void testService() {
        Account account = accountService.createAccount(customer.getId());

        assertNotNull(account);
        assertTrue(account.getId() > 0);
        assertEquals(customer.getId(), account.getCustomerId());
        // TODO: assert account.creationDate()
        assertEquals(0, BigDecimal.ZERO.compareTo(account.getBalance()));
        assertTrue(account.isOpen());

        Account accountFound = accountService.findAccountById(account.getId());

        assertNotNull(accountFound);
        assertEquals(account.getId(), accountFound.getId());
        assertEquals(account.getCustomerId(), accountFound.getCustomerId());
        assertEquals(account.getCreationDate(), accountFound.getCreationDate());
        assertEquals(0, account.getBalance().
                compareTo(accountFound.getBalance()));
        assertEquals(account.isOpen(), accountFound.isOpen());

        accountService.closeAccount(account.getId());

        accountFound = accountService.findAccountById(account.getId());

        assertFalse(accountFound.isOpen());

        accountService.reopenAccount(account.getId());

        accountFound = accountService.findAccountById(account.getId());

        assertTrue(accountFound.isOpen());
    }

}
