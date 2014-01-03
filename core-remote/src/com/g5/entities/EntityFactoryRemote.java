package com.g5.entities;

import com.g5.types.Account;
import com.g5.types.Customer;
import com.g5.types.CustomerCredentials;
import com.g5.types.Payment;
import com.g5.types.Transaction;
import javax.ejb.Remote;

@Remote
public interface EntityFactoryRemote {

    public Customer createCustomer();
    
    public CustomerCredentials createCustomerCredentials();
    
    public Account createAccount();
    
    public Transaction createTransaction();
    
    public Payment createPayment();
    
}
