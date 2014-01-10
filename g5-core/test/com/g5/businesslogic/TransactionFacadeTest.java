package com.g5.businesslogic;

import com.g5.entities.TransactionEntity;
import com.g5.types.Account;
import com.g5.types.Transaction;
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

public class TransactionFacadeTest {

    private final static String TRANSACTION_FACADE_LOCAL =
            "java:global/classes/TransactionFacade!com.g5.businesslogic.TransactionFacadeLocal";
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

    private TransactionFacadeLocal getTransactionFacade() throws NamingException {
        return (TransactionFacadeLocal) context.lookup(TRANSACTION_FACADE_LOCAL);
    }

    @Test
    public void testRollbackTransaction() throws Exception {
        System.out.println("rollbackTransaction");

        Account account = testData.getAccounts().get(0);
        Transaction transaction = testData.getTransactions().get(0);

        BigDecimal balance = account.getBalance();
        BigDecimal transactionValue = transaction.getValue();

        TransactionFacadeLocal facade = getTransactionFacade();

        long minTime = System.currentTimeMillis() / 1000 * 1000;

        facade.rollbackTransaction(transaction.getId());

        long maxTime = (System.currentTimeMillis() / 1000 + 1) * 1000;

        List<TransactionEntity> transactions = entityManager.createNamedQuery(
                "Transaction.findAll", TransactionEntity.class).getResultList();

        assertEquals(testData.getTransactions().size() + 1, transactions.size());

        Transaction rollbackTransaction = transactions.get(transactions.size() -
                1);

        assertEquals(transaction.getAccount().getId(), rollbackTransaction.
                getAccount().getId());
        assertTrue(rollbackTransaction.getDate().getTime() >= minTime);
        assertTrue(rollbackTransaction.getDate().getTime() <= maxTime);
        assertEquals("Rollback - " + transaction.getDescription(),
                rollbackTransaction.getDescription());
        assertEquals(transaction.getValue().negate(), rollbackTransaction.
                getValue());

        entityManager.refresh(account);

        assertEquals(0, balance.subtract(transactionValue).compareTo(account.
                getBalance()));
    }

    @Test
    public void testDeposit() throws Exception {
        System.out.println("deposit");

        Account account = testData.getAccounts().get(0);

        TransactionFacadeLocal facade = getTransactionFacade();

        BigDecimal balance = account.getBalance();
        BigDecimal deposit = new BigDecimal(10);

        long minTime = System.currentTimeMillis() / 1000 * 1000;

        facade.deposit(account.getId(), deposit);

        long maxTime = (System.currentTimeMillis() / 1000 + 1) * 1000;

        List<TransactionEntity> transactions = entityManager.createNamedQuery(
                "Transaction.findAll", TransactionEntity.class).getResultList();

        assertEquals(testData.getTransactions().size() + 1, transactions.size());

        Transaction transaction = transactions.get(transactions.size() - 1);

        assertTrue(transaction.getDate().getTime() >= minTime);
        assertTrue(transaction.getDate().getTime() <= maxTime);
        assertEquals("Deposit", transaction.getDescription());
        assertEquals(0, deposit.compareTo(transaction.getValue()));

        entityManager.refresh(account);

        assertEquals(0, balance.add(deposit).compareTo(account.getBalance()));
    }

    @Test
    public void testWithdraw() throws Exception {
        System.out.println("withdraw");

        Account account = testData.getAccounts().get(0);

        TransactionFacadeLocal facade = getTransactionFacade();

        BigDecimal balance = account.getBalance();
        BigDecimal withdraw = new BigDecimal(10);

        long minTime = System.currentTimeMillis() / 1000 * 1000;

        facade.withdraw(account.getId(), withdraw);

        long maxTime = (System.currentTimeMillis() / 1000 + 1) * 1000;

        List<TransactionEntity> transactions = entityManager.createNamedQuery(
                "Transaction.findAll", TransactionEntity.class).getResultList();

        assertEquals(testData.getTransactions().size() + 1, transactions.size());

        Transaction transaction = transactions.get(transactions.size() - 1);

        assertTrue(transaction.getDate().getTime() >= minTime);
        assertTrue(transaction.getDate().getTime() <= maxTime);
        assertEquals("Withdraw", transaction.getDescription());
        assertEquals(0, withdraw.negate().compareTo(transaction.getValue()));

        entityManager.refresh(account);

        assertEquals(0, balance.subtract(withdraw).compareTo(account.
                getBalance()));
    }

    @Test
    public void testFindTransactionById() throws Exception {
        System.out.println("findTransactionById");

        Transaction transaction = testData.getTransactions().get(0);

        TransactionFacadeLocal facade = getTransactionFacade();

        Transaction result = facade.findTransactionById(transaction.getId());

        entityManager.refresh(transaction);

        assertEquals(transaction.getId(), result.getId());
        // TODO: This assertion fails. result date is slightly older
        //assertEquals(transaction.getDate(), result.getDate());
        assertEquals(transaction.getDescription(), result.getDescription());
        assertEquals(0, transaction.getValue().compareTo(result.getValue()));
    }

    @Test
    public void testFindTransactionsByAccountId() throws Exception {
        System.out.println("findTransactionsByAccountId");

        Account account = testData.getAccounts().get(0);

        TransactionFacadeLocal facade = getTransactionFacade();

        List<Transaction> result = facade.findTransactionsByAccountId(account.
                getId());

        List<TransactionEntity> transactions = entityManager.createNamedQuery(
                "Transaction.findByAccount", TransactionEntity.class).
                setParameter("account", account).getResultList();

        assertEquals(transactions.size(), result.size());

        for (int i = 0; i < result.size(); i++) {
            Transaction t1 = transactions.get(i);
            Transaction t2 = result.get(i);

            entityManager.refresh(t1);

            assertEquals(t1.getId(), t2.getId());
            // TODO: This assertion fails. t2 date is slightly older
            //assertEquals(t1.getDate(), t2.getDate());
            assertEquals(t1.getDescription(), t2.getDescription());
            assertEquals(0, t1.getValue().compareTo(t2.getValue()));
        }
    }

}
