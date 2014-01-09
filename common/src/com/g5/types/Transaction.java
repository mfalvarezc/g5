package com.g5.types;

import java.math.BigDecimal;
import java.util.Date;

public interface Transaction {

    Long getId();

    void setId(Long id);

    Account getAccount();

    void setAccount(Account account);

    String getDescription();

    void setDescription(String description);

    Date getDate();

    void setDate(Date date);

    BigDecimal getValue();

    void setValue(BigDecimal value);

}
