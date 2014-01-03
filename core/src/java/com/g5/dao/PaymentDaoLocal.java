package com.g5.dao;

import com.g5.types.Payment;
import com.g5.types.Transaction;
import javax.ejb.Local;

@Local
public interface PaymentDaoLocal extends BaseDaoLocal<Payment> {

    public Payment findByTransaction(Transaction transaction);
    
}
