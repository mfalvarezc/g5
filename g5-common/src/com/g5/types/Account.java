package com.g5.types;

import java.math.BigDecimal;
import java.util.Date;

public interface Account {

    Long getId();

    void setId(Long id);

    Date getCreationDate();

    void setCreationDate(Date creationDate);

    BigDecimal getBalance();

    void setBalance(BigDecimal balance);

    Customer getCustomer();

    void setCustomer(Customer customer);

    boolean isOpen();

    void setOpen(boolean open);
}
