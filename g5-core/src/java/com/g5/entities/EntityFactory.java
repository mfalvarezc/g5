package com.g5.entities;

import com.g5.types.Account;
import com.g5.types.Customer;
import com.g5.types.Group;
import com.g5.types.User;
import com.g5.types.Payment;
import com.g5.types.Transaction;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
public class EntityFactory implements EntityFactoryLocal {

    @Override
    public Customer createCustomer() {
        return new CustomerEntity();
    }

    @Override
    public User createUser() {
        return new UserEntity();
    }

    @Override
    public Group createGroup() {
        return new GroupEntity();
    }

    @Override
    public Account createAccount() {
        return new AccountEntity();
    }

    @Override
    public Transaction createTransaction() {
        return new TransactionEntity();
    }

    @Override
    public Payment createPayment() {
        return new PaymentEntity();
    }

}
