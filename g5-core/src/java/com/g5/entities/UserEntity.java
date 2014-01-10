package com.g5.entities;

import com.g5.types.Group;
import com.g5.types.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

@Entity(name = "User")
@Table(name = "USER_")
@NamedQueries({
    @NamedQuery(name = "User.findByUsername", query =
            "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findAll", query =
            "SELECT u FROM User u"),
    @NamedQuery(name = "User.removeAll", query =
            "DELETE FROM User")
})
@TableGenerator(name = "UserSequence", initialValue = 1)
public class UserEntity implements User,
        Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator =
            "UserSequence")
    private Long id;
    @ManyToMany(targetEntity = GroupEntity.class)
    @JoinTable(name = "GROUP_USER", joinColumns = {
        @JoinColumn(name = "USER_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "GROUP_ID")})
    private List<Group> groups = new ArrayList<>();
    private String username;
    private String salt;
    @Column(name = "HASH_")
    private String hash;
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
    public List<Group> getGroups() {
        return groups;
    }

    @Override
    public void setGroups(List<Group> groups) {
        this.groups = groups;
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
    public String getHash() {
        return hash;
    }

    @Override
    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash_ = 7;
        hash_ = 59 * hash_ + Objects.hashCode(this.id);
        return hash_;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserEntity other = (UserEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "UserEntity{" + "id=" + id + ", username=" + username + ", salt=" +
                salt + ", hash=" + hash + '}';
    }

}
