package com.g5.entities;

import com.g5.types.Account;
import com.g5.types.Transaction;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
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

@Entity(name = "Transaction")
@Table(name = "TRANSACTION_")
@NamedQueries({
    @NamedQuery(name = "Transaction.findByAccount", query = "SELECT t FROM Transaction t WHERE t.account = :account"),
    @NamedQuery(name = "Transaction.removeByAccount", query = "DELETE FROM Transaction t WHERE t.account = :account"),
    @NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t"),
    @NamedQuery(name = "Transaction.removeAll", query = "DELETE FROM Transaction")})
@TableGenerator(name = "TransactionSequence", initialValue = 1)
public class TransactionEntity implements Transaction, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TransactionSequence")
    private Long id;
    @ManyToOne(targetEntity = AccountEntity.class, optional = false)
    private Account account;
    private String description;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "DATE_")
    private Date date;
    @Column(name = "VALUE_")
    private BigDecimal value;
    @Version
    private int version;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final TransactionEntity other = (TransactionEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "TransactionEntity{" + "id=" + id + ", description=" + description + ", date=" + date + ", value=" + value + '}';
    }

}
