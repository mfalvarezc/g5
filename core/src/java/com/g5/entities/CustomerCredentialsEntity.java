package com.g5.entities;

import com.g5.types.Customer;
import com.g5.types.CustomerCredentials;
import java.io.Serializable;
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

@Entity(name = "CustomerCredentials")
@Table(name = "CUSTOMER_CREDENTIALS")
@NamedQueries({
    @NamedQuery(name = "CustomerCredentials.findByCustomer", query = "SELECT cc FROM CustomerCredentials cc WHERE cc.customer = :customer"),
    @NamedQuery(name = "CustomerCredentials.findByUsernameAndKey", query = "SELECT cc FROM CustomerCredentials cc WHERE cc.username = :username AND cc.key = :key"),
    @NamedQuery(name = "CustomerCredentials.findAll", query = "SELECT cc FROM CustomerCredentials cc"),
    @NamedQuery(name = "CustomerCredentials.removeAll", query = "DELETE FROM CustomerCredentials")
})
@TableGenerator(name = "CustomerCredentialsSequence", initialValue = 1)
public class CustomerCredentialsEntity implements CustomerCredentials, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "CustomerCredentialsSequence")
    private Long id;
    @OneToOne(targetEntity = CustomerEntity.class, optional = false)
    private Customer customer;
    private String username;
    private String salt;
    @Column(name = "KEY_")
    private String key;
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
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getSalt() {
        return salt;
    }

    @Override
    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
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
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final CustomerCredentialsEntity other = (CustomerCredentialsEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "CustomerCredentialsEntity{" + "id=" + id + ", customer=" + customer + ", username=" + username + ", salt=" + salt + ", _key=" + key + '}';
    }

}
