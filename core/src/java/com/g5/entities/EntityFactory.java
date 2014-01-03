package com.g5.entities;

import com.g5.types.Account;
import com.g5.types.Customer;
import com.g5.types.CustomerCredentials;
import com.g5.types.Payment;
import com.g5.types.Transaction;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
public class EntityFactory implements EntityFactoryLocal, EntityFactoryRemote {

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Customer createCustomer() {
        return new CustomerEntity();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CustomerCredentials createCustomerCredentials() {
        return new CustomerCredentialsEntity();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Account createAccount() {
        return new AccountEntity();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Transaction createTransaction() {
        return new TransactionEntity();
    }

    @Override
    public Payment createPayment() {
        return new PaymentEntity();
    }

}
