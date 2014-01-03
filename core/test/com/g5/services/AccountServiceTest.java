package com.g5.services;

import com.g5.entities.AccountEntity;
import com.g5.types.Account;
import com.g5.types.Customer;
import java.math.BigDecimal;
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

public class AccountServiceTest {

    private final static String ACCOUNT_SERVICE_LOCAL = "java:global/classes/AccountService!com.g5.services.AccountServiceLocal";
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
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        entityManager = entityManagerFactory.createEntityManager();
        testData = TestData.reset(entityManager);
    }

    @After
    public void tearDown() {
        entityManager.clear();
        entityManager.close();
        entityManagerFactory.close();
    }

    private AccountServiceLocal getAccountService() throws NamingException {
        return (AccountServiceLocal) context.lookup(ACCOUNT_SERVICE_LOCAL);
    }

    @Test
    public void testCreate() throws Exception {
        System.out.println("create");

        Customer customer = testData.getCustomers().get(0);

        AccountServiceLocal service = getAccountService();

        long minTime = System.currentTimeMillis() / 1000 * 1000;

        Account result = service.create(customer.getId());

        long maxTime = (System.currentTimeMillis() / 1000 + 1) * 1000;

        entityManager.refresh(customer);

        List<AccountEntity> accounts = entityManager.createNamedQuery("Account.findByCustomer", AccountEntity.class).setParameter("customer", customer).getResultList();

        assertEquals(testData.getAccounts().size() + 1, accounts.size());

        Account account = accounts.get(accounts.size() - 1);

        assertEquals(account.getId(), result.getId());

        assertTrue(result.getCreationDate().getTime() >= minTime);
        assertTrue(result.getCreationDate().getTime() <= maxTime);
        assertEquals(BigDecimal.ZERO, result.getBalance());
    }

    @Test
    public void testFindById() throws Exception {
        System.out.println("findById");

        Account account = testData.getAccounts().get(0);

        entityManager.refresh(account);

        AccountServiceLocal service = getAccountService();

        Account result = service.findById(account.getId());

        assertEquals(account.getId(), result.getId());
        assertEquals(account.getCreationDate(), result.getCreationDate());
        assertEquals(account.getBalance(), result.getBalance());
    }

    @Test
    public void testCloseAndReopen() throws Exception {
        System.out.println("closeAndReopen");

        Account account = testData.getAccounts().get(0);

        AccountServiceLocal service = getAccountService();

        service.close(account.getId());

        entityManager.refresh(account);

        assertFalse(account.isOpen());

        service.reopen(account.getId());

        entityManager.refresh(account);

        assertTrue(account.isOpen());
    }

}
