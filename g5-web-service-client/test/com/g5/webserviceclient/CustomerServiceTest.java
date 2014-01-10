package com.g5.webserviceclient;

import com.g5.webserviceclient.customer.Customer;
import com.g5.webserviceclient.customer.CustomerService;
import com.g5.webserviceclient.customer.CustomerService_Service;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CustomerServiceTest {

    private CustomerService customerService;

    @Before
    public void setUpClass() {
        TestHelper.configureTrustStore();
        customerService = (new CustomerService_Service()).
                getCustomerServiceImplPort();
    }

    @Test
    public void testService() {
        final String username = TestHelper.getRandomUsername();
        final String password = "Cu$tom3r";
        final String newPassword = "N3wPa$$word";

        Customer customer = customerService.createCustomer(username, password);

        assertNotNull(customer);
        assertTrue(customer.getId() > 0);
        assertTrue(customer.isEnabled());

        Customer customerFound = customerService.findCustomerById(customer.
                getId());

        assertNotNull(customerFound);
        assertEquals(customer.getId(), customerFound.getId());
        assertEquals(customer.isEnabled(), customerFound.isEnabled());

        customerService.changeCustomerPassword(customer.getId(), newPassword);

        customerService.disableCustomer(customer.getId());

        customerFound = customerService.findCustomerById(customer.getId());

        assertFalse(customerFound.isEnabled());

        customerService.enableCustomer(customer.getId());

        customerFound = customerService.findCustomerById(customer.getId());

        assertTrue(customerFound.isEnabled());
    }

}
