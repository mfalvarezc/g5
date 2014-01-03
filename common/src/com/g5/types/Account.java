package com.g5.types;

import java.math.BigDecimal;
import java.util.Date;

public interface Account {

    public Long getId();

    public void setId(Long id);

    public Date getCreationDate();

    public void setCreationDate(Date creationDate);

    public BigDecimal getBalance();

    public void setBalance(BigDecimal balance);

    public Customer getCustomer();

    public void setCustomer(Customer customer);

    public boolean isOpen();

    public void setOpen(boolean open);
}
