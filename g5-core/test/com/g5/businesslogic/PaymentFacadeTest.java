package com.g5.businesslogic;

import com.g5.entities.PaymentEntity;
import com.g5.entities.TransactionEntity;
import com.g5.types.Account;
import com.g5.types.Payment;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PaymentFacadeTest {

    private final static String PAYMENT_FACADE_LOCAL =
            "java:global/classes/PaymentFacade!com.g5.businesslogic.PaymentFacadeLocal";
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

    private PaymentFacadeLocal getPaymentFacade() throws NamingException {
        return (PaymentFacadeLocal) context.lookup(PAYMENT_FACADE_LOCAL);
    }

    @Test
    public void testRollbackPayment() throws Exception {
        System.out.println("testRollbackPayment");

        Payment payment = testData.getPayments().get(1);
        Account receiverAccount = payment.getReceiverAccount();
        Account senderAccount = payment.getSenderAccount();

        BigDecimal newReceiverBalance = receiverAccount.getBalance().subtract(
                payment.getValue());
        BigDecimal newSenderBalance = senderAccount.getBalance().add(payment.
                getValue());

        PaymentFacadeLocal facade = getPaymentFacade();

        long minTime = System.currentTimeMillis() / 1000 * 1000;

        facade.rollbackPayment(payment.getId());

        long maxTime = (System.currentTimeMillis() / 1000 + 1) * 1000;

        List<TransactionEntity> transactions = entityManager.createNamedQuery(
                "Transaction.findAll", TransactionEntity.class).getResultList();

        assertEquals(testData.getTransactions().size() + 2, transactions.size());

        Transaction transaction = transactions.get(transactions.size() - 2);

        assertEquals(receiverAccount.getId(), transaction.getAccount().getId());
        assertTrue(transaction.getDate().getTime() >= minTime);
        assertTrue(transaction.getDate().getTime() <= maxTime);
        assertEquals("Rollback - " + payment.getDescription(), transaction.
                getDescription());
        assertEquals(payment.getValue().negate(), transaction.getValue());

        transaction = transactions.get(transactions.size() - 1);

        assertEquals(senderAccount.getId(), transaction.getAccount().getId());
        assertTrue(transaction.getDate().getTime() >= minTime);
        assertTrue(transaction.getDate().getTime() <= maxTime);
        assertEquals("Rollback - " + payment.getDescription(), transaction.
                getDescription());
        assertEquals(payment.getValue(), transaction.getValue());

        entityManager.refresh(receiverAccount);

        assertEquals(newReceiverBalance, receiverAccount.getBalance());

        entityManager.refresh(senderAccount);

        assertEquals(newSenderBalance, senderAccount.getBalance());
    }

    @Test
    public void testRequestPayment() throws Exception {
        System.out.println("requestPayment");

        Account account = testData.getAccounts().get(0);

        PaymentFacadeLocal facade = getPaymentFacade();

        String description = "Payment description";
        BigDecimal value = new BigDecimal(10);

        facade.requestPayment(account.getId(), description, value);

        List<PaymentEntity> payments = entityManager.createNamedQuery(
                "Payment.findAll", PaymentEntity.class).getResultList();

        assertEquals(testData.getPayments().size() + 1, payments.size());

        Payment payment = payments.get(payments.size() - 1);

        assertTrue(payment.getId() >= 1);
        assertEquals(account.getId(), payment.getReceiverAccount().getId());
        assertNull(payment.getReceiverTransaction());
        assertNull(payment.getSenderAccount());
        assertNull(payment.getSenderTransaction());
        assertEquals(description, payment.getDescription());
        assertEquals(0, value.compareTo(payment.getValue()));
    }

    @Test
    public void testApprovePayment() throws Exception {
        System.out.println("approvePayment");

        Payment payment = testData.getPayments().get(0);
        Account receiverAccount = testData.getAccounts().get(0);
        Account senderAccount = testData.getAccounts().get(1);

        System.out.println("Current receiver balance: " + receiverAccount.
                getBalance());
        System.out.println("Current sender balance: " + senderAccount.
                getBalance());

        BigDecimal newReceiverBalance = receiverAccount.getBalance().add(
                payment.getValue());
        BigDecimal newSenderBalance = senderAccount.getBalance().subtract(
                payment.getValue());

        System.out.println("New receiver balance: " + newReceiverBalance);
        System.out.println("New sender balance: " + newSenderBalance);

        PaymentFacadeLocal facade = getPaymentFacade();

        long minTime = System.currentTimeMillis() / 1000 * 1000;

        facade.approvePayment(payment.getId(), senderAccount.getId(), payment.
                getValue());

        long maxTime = (System.currentTimeMillis() / 1000 + 1) * 1000;

        List<TransactionEntity> transactions = entityManager.createNamedQuery(
                "Transaction.findAll", TransactionEntity.class).getResultList();

        assertEquals(testData.getTransactions().size() + 2, transactions.size());

        Transaction senderTransaction = transactions.
                get(transactions.size() - 2);

        assertEquals(senderAccount.getId(), senderTransaction.getAccount().
                getId());
        assertTrue(senderTransaction.getDate().getTime() >= minTime);
        assertTrue(senderTransaction.getDate().getTime() <= maxTime);
        assertEquals(payment.getDescription(), senderTransaction.
                getDescription());
        assertEquals(payment.getValue().negate(), senderTransaction.getValue());

        Transaction receiverTransaction = transactions.get(transactions.size() -
                1);

        assertEquals(receiverAccount.getId(), receiverTransaction.getAccount().
                getId());
        assertTrue(receiverTransaction.getDate().getTime() >= minTime);
        assertTrue(receiverTransaction.getDate().getTime() <= maxTime);
        assertEquals(payment.getDescription(), receiverTransaction.
                getDescription());
        assertEquals(payment.getValue(), receiverTransaction.getValue());

        entityManager.refresh(payment);

        assertEquals(receiverAccount.getId(), payment.getReceiverAccount().
                getId());
        assertEquals(receiverTransaction.getId(), payment.
                getReceiverTransaction().getId());
        assertEquals(senderAccount.getId(), payment.getSenderAccount().getId());
        assertEquals(senderTransaction.getId(), payment.getSenderTransaction().
                getId());

        entityManager.refresh(receiverAccount);

        assertEquals(0, newReceiverBalance.compareTo(receiverAccount.
                getBalance()));

        entityManager.refresh(senderAccount);

        assertEquals(0, newSenderBalance.compareTo(senderAccount.getBalance()));
    }

}
