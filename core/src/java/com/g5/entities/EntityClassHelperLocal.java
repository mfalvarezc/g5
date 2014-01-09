package com.g5.entities;

import com.g5.types.Account;
import com.g5.types.Customer;
import com.g5.types.Group;
import com.g5.types.User;
import com.g5.types.Payment;
import com.g5.types.Transaction;
import javax.ejb.Local;

@Local
public interface EntityClassHelperLocal {

    Class<? extends Account> getAccountClass();

    Class<? extends Customer> getCustomerClass();

    Class<? extends User> getUserClass();

    Class<? extends Group> getGroupClass();

    Class<? extends Payment> getPaymentClass();

    Class<? extends Transaction> getTransactionClass();

}
