package com.g5.entities;

import com.g5.types.Account;
import com.g5.types.Customer;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.Version;

@Entity(name = "Account")
@Table(name = "ACCOUNT")
@NamedQueries({
    @NamedQuery(name = "Account.findByCustomer", query = "SELECT a FROM Account a WHERE a.customer = :customer"),
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.removeAll", query = "DELETE FROM Account")})
@TableGenerator(name = "AccountSequence", initialValue = 1)
public class AccountEntity implements Account, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "AccountSequence")
    private Long id;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;
    private BigDecimal balance;
    @ManyToOne(targetEntity = CustomerEntity.class, optional = false)
    private Customer customer;
    @Version
    private int version;
    private boolean open = true;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final AccountEntity other = (AccountEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AccountEntity{" + "id=" + id + ", creationDate=" + creationDate + ", balance=" + balance + '}';
    }

}
