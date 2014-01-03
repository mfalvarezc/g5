package com.g5.types;

import java.math.BigDecimal;
import java.util.Date;

public interface Transaction {

    public Long getId();

    public void setId(Long id);

    public Account getAccount();

    public void setAccount(Account account);

    public String getDescription();

    public void setDescription(String description);

    public Date getDate();

    public void setDate(Date date);

    public BigDecimal getValue();

    public void setValue(BigDecimal value);

}
