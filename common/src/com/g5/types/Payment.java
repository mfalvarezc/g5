package com.g5.types;

import java.math.BigDecimal;

public interface Payment {

    Long getId();

    void setId(Long id);

    Account getReceiverAccount();

    void setReceiverAccount(Account receiverAccount);

    Transaction getReceiverTransaction();

    void setReceiverTransaction(Transaction receiverTransaction);

    Transaction getSenderTransaction();

    void setSenderTransaction(Transaction senderTransaction);

    Account getSenderAccount();

    void setSenderAccount(Account senderAccount);

    String getDescription();

    void setDescription(String description);

    BigDecimal getValue();

    void setValue(BigDecimal value);

}
