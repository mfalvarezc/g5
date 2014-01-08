package com.g5.entities;

import com.g5.types.Customer;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

@Entity(name = "Customer")
@Table(name = "CUSTOMER")
@NamedQueries({
    @NamedQuery(name = "Customer.findByAccount", query =
            "SELECT a.customer FROM Account a WHERE a = :account"),
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.removeAll", query = "DELETE FROM Customer")})
@TableGenerator(name = "CustomerSequence", initialValue = 1)
public class CustomerEntity implements Customer, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator =
            "CustomerSequence")
    private Long id;
    @Version
    private int version;
    private boolean enabled = true;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.id);
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
        final CustomerEntity other = (CustomerEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "CustomerEntity{" + "id=" + id + '}';
    }

}
