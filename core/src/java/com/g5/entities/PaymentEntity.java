package com.g5.entities;

import com.g5.types.Account;
import com.g5.types.Payment;
import com.g5.types.Transaction;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

@Entity(name = "Payment")
@Table(name = "PAYMENT")
@NamedQueries({
    @NamedQuery(name = "Payment.findByTransaction", query = "SELECT p FROM Payment p WHERE p.receiverTransaction = :transaction OR p.senderTransaction = :transaction"),
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p"),
    @NamedQuery(name = "Payment.removeAll", query = "DELETE FROM Payment")
})
@TableGenerator(name = "PaymentSequence", initialValue = 1)
public class PaymentEntity implements Payment, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PaymentSequence")
    private Long id;
    @OneToOne(targetEntity = AccountEntity.class, optional = false)
    private Account receiverAccount;
    @OneToOne(targetEntity = TransactionEntity.class)
    private Transaction receiverTransaction;
    @OneToOne(targetEntity = AccountEntity.class)
    private Account senderAccount;
    @OneToOne(targetEntity = TransactionEntity.class)
    private Transaction senderTransaction;
    private String description;
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
    public Account getReceiverAccount() {
        return receiverAccount;
    }

    @Override
    public void setReceiverAccount(Account receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    @Override
    public Transaction getReceiverTransaction() {
        return receiverTransaction;
    }

    @Override
    public void setReceiverTransaction(Transaction receiverTransaction) {
        this.receiverTransaction = receiverTransaction;
    }

    @Override
    public Account getSenderAccount() {
        return senderAccount;
    }

    @Override
    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    @Override
    public Transaction getSenderTransaction() {
        return senderTransaction;
    }

    @Override
    public void setSenderTransaction(Transaction senderTransaction) {
        this.senderTransaction = senderTransaction;
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
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final PaymentEntity other = (PaymentEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "PaymentEntity{" + "id=" + id + ", description=" + description + ", value=" + value + '}';
    }

}
