package com.g5.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "account")
public class AccountDto {

    private long id;
    private long customerId;
    private Date creationDate;
    private BigDecimal balance;
    private boolean open;

    @XmlElement(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 83 * hash + (int) (this.customerId ^ (this.customerId >>> 32));
        hash = 83 * hash + Objects.hashCode(this.creationDate);
        hash = 83 * hash + Objects.hashCode(this.balance);
        hash = 83 * hash + (this.open ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccountDto other = (AccountDto) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.customerId != other.customerId) {
            return false;
        }
        if (!Objects.equals(this.creationDate, other.creationDate)) {
            return false;
        }
        if (!Objects.equals(this.balance, other.balance)) {
            return false;
        }
        return this.open == other.open;
    }

    @Override
    public String toString() {
        return "AccountDto{" + "id=" + id + ", customerId=" + customerId +
                ", creationDate=" + creationDate + ", balance=" + balance +
                ", open=" + open + '}';
    }

}
