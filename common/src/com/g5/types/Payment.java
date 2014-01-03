package com.g5.types;

import java.math.BigDecimal;

public interface Payment {

    public Long getId();

    public void setId(Long id);

    public Account getReceiverAccount();

    public void setReceiverAccount(Account receiverAccount);

    public Transaction getReceiverTransaction();

    public void setReceiverTransaction(Transaction receiverTransaction);

    public Transaction getSenderTransaction();

    public void setSenderTransaction(Transaction senderTransaction);

    public Account getSenderAccount();

    public void setSenderAccount(Account senderAccount);

    public String getDescription();

    public void setDescription(String description);

    public BigDecimal getValue();

    public void setValue(BigDecimal value);

}
