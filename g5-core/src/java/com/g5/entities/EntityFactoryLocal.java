package com.g5.entities;

import com.g5.types.Account;
import com.g5.types.Customer;
import com.g5.types.Group;
import com.g5.types.User;
import com.g5.types.Payment;
import com.g5.types.Transaction;
import javax.ejb.Local;

@Local
public interface EntityFactoryLocal {

    Customer createCustomer();

    User createUser();

    Group createGroup();

    Account createAccount();

    Transaction createTransaction();

    Payment createPayment();
}
