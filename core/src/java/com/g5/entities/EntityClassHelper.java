package com.g5.entities;

import com.g5.types.Account;
import com.g5.types.Customer;
import com.g5.types.CustomerCredentials;
import com.g5.types.Payment;
import com.g5.types.Transaction;
import javax.ejb.Stateless;

@Stateless
public class EntityClassHelper implements EntityClassHelperLocal {

    @Override
    public Class<? extends Account> getAccountClass() {
        return AccountEntity.class;
    }

    @Override
    public Class<? extends Customer> getCustomerClass() {
        return CustomerEntity.class;
    }

    @Override
    public Class<? extends CustomerCredentials> getCustomerCredentialsClass() {
        return CustomerCredentials.class;
    }

    @Override
    public Class<? extends Payment> getPaymentClass() {
        return PaymentEntity.class;
    }

    @Override
    public Class<? extends Transaction> getTransactionClass() {
        return TransactionEntity.class;
    }

}
