package com.g5.services;

import com.g5.entities.AccountEntity;
import com.g5.entities.CustomerCredentialsEntity;
import com.g5.entities.CustomerEntity;
import com.g5.entities.PaymentEntity;
import com.g5.entities.TransactionEntity;
import com.g5.types.Account;
import com.g5.types.Customer;
import com.g5.types.CustomerCredentials;
import com.g5.types.Payment;
import com.g5.types.Transaction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

public final class TestData {

    private final static String[] ENTITY_NAMES = {"Payment", "Transaction", "CustomerCredentials", "Account", "Customer"};

    private List<Customer> customers;
    private List<CustomerCredentials> customerCredentials;
    private List<Account> accounts;
    private List<Transaction> transactions;
    private List<Payment> payments;

    public TestData(List<Customer> customers, List<CustomerCredentials> customerCredentials, List<Account> accounts, List<Transaction> transactions, List<Payment> payments) {
        this.customers = customers;
        this.customerCredentials = customerCredentials;
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
            entityManager.createNamedQuery(entityName + ".removeAll").executeUpdate();
        }
    }

    private static TestData insertTestData(EntityManager entityManager) {
        List<Customer> customerList = new ArrayList<>(1);

        Customer customer = new CustomerEntity();
        entityManager.persist(customer);
        customerList.add(customer);

        List<CustomerCredentials> customerCredentialsList = new ArrayList<>(1);
        CustomerCredentials customerCredentials = new CustomerCredentialsEntity();
        customerCredentials.setCustomer(customer);
        customerCredentials.setSalt("salt");
        customerCredentials.setKey("key");
        entityManager.persist(customerCredentials);
        customerCredentialsList.add(customerCredentials);

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

        return new TestData(customerList, customerCredentialsList, accountList, transactionList, paymentList);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<CustomerCredentials> getCustomerCredentials() {
        return customerCredentials;
    }

    public void setCustomerCredentials(List<CustomerCredentials> customerCredentials) {
        this.customerCredentials = customerCredentials;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

}
