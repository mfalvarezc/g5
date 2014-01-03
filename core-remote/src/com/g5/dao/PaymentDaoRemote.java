package com.g5.dao;

import com.g5.types.Payment;
import com.g5.types.Transaction;
import javax.ejb.Remote;

@Remote
public interface PaymentDaoRemote extends BaseDaoRemote<Payment> {

    public Payment findByTransaction(Transaction transaction);

}
