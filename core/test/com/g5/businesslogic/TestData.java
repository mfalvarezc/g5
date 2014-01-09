package com.g5.businesslogic;

import com.g5.businesslogic.groups.GroupName;
import com.g5.entities.AccountEntity;
import com.g5.entities.UserEntity;
import com.g5.entities.CustomerEntity;
import com.g5.entities.GroupEntity;
import com.g5.entities.PaymentEntity;
import com.g5.entities.TransactionEntity;
import com.g5.types.Account;
import com.g5.types.Customer;
import com.g5.types.Group;
import com.g5.types.User;
import com.g5.types.Payment;
import com.g5.types.Transaction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

public final class TestData {

    private final static String[] ENTITY_NAMES = {"Payment", "Transaction",
        "Account", "Customer", "User", "Group"};

    private final List<Group> groups;
    private final List<User> users;
    private final List<Customer> customers;
    private final List<Account> accounts;
    private final List<Transaction> transactions;
    private final List<Payment> payments;

    public TestData(List<Group> groups, List<User> users,
            List<Customer> customers, List<Account> accounts,
            List<Transaction> transactions, List<Payment> payments) {
        this.groups = groups;
        this.users = users;
        this.customers = customers;
        this.accounts = accounts;
        this.transactions = transactions;
        this.payments = payments;
    }

    public static TestData reset(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        removeAll(entityManager);

        TestData testData = insertTestData(entityManager);

        entityManager.getTransaction().commit();

        return testData;
    }

    private static void removeAll(EntityManager entityManager) {
        for (String entityName : ENTITY_NAMES) {
            entityManager.createNamedQuery(entityName + ".removeAll").
                    executeUpdate();
        }
    }

    private static TestData insertTestData(EntityManager entityManager) {
        List<Group> groupList = new ArrayList<>(1);

        Group customerGroup = new GroupEntity();
        customerGroup.setName(GroupName.CUSTOMERS.toString());
        entityManager.persist(customerGroup);
        groupList.add(customerGroup);

        List<User> userList = new ArrayList<>(1);
        User customerUser = new UserEntity();
        customerUser.setSalt("salt");
        customerUser.setHash("hash");
        customerUser.getGroups().add(customerGroup);
        entityManager.persist(customerUser);
        userList.add(customerUser);

        List<Customer> customerList = new ArrayList<>(1);
        Customer customer = new CustomerEntity();
        customer.setUser(customerUser);
        entityManager.persist(customer);
        customerList.add(customer);

        List<Account> accountList = new ArrayList<>(2);
        Account account1 = new AccountEntity();
        account1.setCustomer(customer);
        account1.setCreationDate(new Date());
        account1.setBalance(new BigDecimal(100));
        entityManager.persist(account1);
        accountList.add(account1);

        Account account2 = new AccountEntity();
        account2.setCustomer(customer);
        account2.setCreationDate(new Date());
        account2.setBalance(new BigDecimal(100));
        entityManager.persist(account2);
        accountList.add(account2);

        List<Transaction> transactionList = new ArrayList<>(1);
        Transaction transaction1 = new TransactionEntity();
        transaction1.setAccount(account1);
        transaction1.setDate(new Date());
        transaction1.setDescription("Deposit");
        transaction1.setValue(new BigDecimal(100));
        entityManager.persist(transaction1);
        transactionList.add(transaction1);

        Transaction transaction2 = new TransactionEntity();
        transaction2.setAccount(account1);
        transaction2.setDate(new Date());
        transaction2.setDescription("Payment");
        transaction2.setValue(new BigDecimal(10));
        entityManager.persist(transaction2);
        transactionList.add(transaction2);

        Transaction transaction3 = new TransactionEntity();
        transaction3.setAccount(account2);
        transaction3.setDate(new Date());
        transaction3.setDescription("Payment");
        transaction3.setValue(new BigDecimal(-10));
        entityManager.persist(transaction3);
        transactionList.add(transaction3);

        List<Payment> paymentList = new ArrayList<>(1);
        Payment payment1 = new PaymentEntity();
        payment1.setReceiverAccount(account1);
        payment1.setDescription("Payment");
        payment1.setValue(new BigDecimal(10));
        entityManager.persist(payment1);
        paymentList.add(payment1);

        Payment payment2 = new PaymentEntity();
        payment2.setReceiverAccount(account1);
        payment2.setReceiverTransaction(transaction2);
        payment2.setSenderAccount(account2);
        payment2.setSenderTransaction(transaction3);
        payment2.setDescription("Payment");
        payment2.setValue(new BigDecimal(10));
        entityManager.persist(payment2);
        paymentList.add(payment2);

        return new TestData(groupList, userList, customerList, accountList,
                transactionList, paymentList);
    }

    public List<Group> getGroups() {
        return groups;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Payment> getPayments() {
        return payments;
    }

}
