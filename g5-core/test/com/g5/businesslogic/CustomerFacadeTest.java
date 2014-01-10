package com.g5.businesslogic;

import com.g5.entities.AccountEntity;
import com.g5.entities.UserEntity;
import com.g5.entities.CustomerEntity;
import com.g5.types.Account;
import com.g5.types.Customer;
import com.g5.types.User;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CustomerFacadeTest {

    private final static String CUSTOMER_FACADE_LOCAL =
            "java:global/classes/CustomerFacade!com.g5.businesslogic.CustomerFacadeLocal";
    private final static String PERSISTENCE_UNIT = "g5-non-jta";

    private static EJBContainer container;
    private static Context context;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private TestData testData;

    @BeforeClass
    public static void setUpClass() {
        container = EJBContainer.createEJBContainer();
        context = container.getContext();
    }

    @AfterClass
    public static void tearDownClass() {
        container.close();
    }

    @Before
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory(
                PERSISTENCE_UNIT);
        entityManager = entityManagerFactory.createEntityManager();
        testData = TestData.reset(entityManager);
    }

    @After
    public void tearDown() {
        entityManager.clear();
        entityManager.close();
        entityManagerFactory.close();
    }

    private CustomerFacadeLocal getCustomerFacade() throws NamingException {
        return (CustomerFacadeLocal) context.lookup(CUSTOMER_FACADE_LOCAL);
    }

    @Test
    public void testChangeCustomerPassword() throws Exception {
        System.out.println("changeCustomerPassword");

        Customer customer = testData.getCustomers().get(0);
        User user = testData.getUsers().get(0);

        String username = user.getUsername();
        String salt = user.getSalt();
        String hash = user.getHash();

        CustomerFacadeLocal facade = (CustomerFacadeLocal) getCustomerFacade();

        facade.changeCustomerPassword(customer.getId(), "MyN3wPa$$word");

        entityManager.refresh(user);

        assertEquals(username, user.getUsername());
        assertFalse(salt.equals(user.getSalt()));
        assertEquals(32, user.getSalt().length());
        assertFalse(hash.equals(user.getHash()));
        assertEquals(32, user.getHash().length());
    }

    @Test
    public void testCreateCustomer() throws Exception {
        System.out.println("createCustomer");

        CustomerFacadeLocal facade = getCustomerFacade();

        String username = "username";
        String password = "MyP4$$word";

        Customer result = facade.createCustomer(username, password);

        assertNotNull(result);
        assertTrue(result.getId() >= 1);

        List<CustomerEntity> customers = entityManager.createNamedQuery(
                "Customer.findAll", CustomerEntity.class).getResultList();

        assertEquals(testData.getCustomers().size() + 1, customers.size());

        Customer customer = customers.get(customers.size() - 1);

        assertEquals(customer.getId(), result.getId());

        User user = customer.getUser();

        assertTrue(user.getId() >= 1);
        assertEquals(username, user.getUsername());
        assertNotNull(user.getSalt());
        assertEquals(32, user.getSalt().length());
        assertNotNull(user.getHash());
        assertEquals(32, user.getHash().length());
    }

    @Test
    public void testFindCustomerById() throws Exception {
        System.out.println("findCustomerById");

        Customer customer = testData.getCustomers().get(0);

        CustomerFacadeLocal facade = getCustomerFacade();

        Customer result = facade.findCustomerById(customer.getId());

        assertEquals(customer.getId(), result.getId());
    }

    @Test
    public void testDisableAndEnableCustomer() throws Exception {
        System.out.println("disableAndEnableCustomer");

        Customer customer = testData.getCustomers().get(0);

        CustomerFacadeLocal facade = getCustomerFacade();

        facade.disableCustomer(customer.getId());

        entityManager.refresh(customer);

        assertFalse(customer.isEnabled());

        List<AccountEntity> accounts = entityManager.createNamedQuery(
                "Account.findByCustomer", AccountEntity.class).setParameter(
                        "customer", customer).getResultList();

        assertFalse(accounts.isEmpty());

        for (Account account : accounts) {
            entityManager.refresh(account);
            assertFalse(account.isOpen());
        }

        facade.enableCustomer(customer.getId());

        entityManager.refresh(customer);

        assertTrue(customer.isEnabled());

        accounts = entityManager.createNamedQuery("Account.findByCustomer",
                AccountEntity.class).setParameter("customer", customer).
                getResultList();

        assertFalse(accounts.isEmpty());

        for (Account account : accounts) {
            entityManager.refresh(account);
            assertTrue(account.isOpen());
        }
    }
}
