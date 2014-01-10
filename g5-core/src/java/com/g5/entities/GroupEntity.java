package com.g5.entities;

import com.g5.types.Group;
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

@Entity(name = "Group")
@Table(name = "GROUP_")
@NamedQueries({
    @NamedQuery(name = "Group.findByName", query =
            "SELECT g FROM Group g WHERE g.name = :name"),
    @NamedQuery(name = "Group.findAll", query = "SELECT g FROM Group g"),
    @NamedQuery(name = "Group.removeAll", query = "DELETE FROM Group"),})
@TableGenerator(name = "GroupSequence", initialValue = 1)
public class GroupEntity implements Group, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator =
            "GroupSequence")
    private Long id;
    private String name;
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
        hash = 61 * hash + Objects.hashCode(this.id);
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
        final GroupEntity other = (GroupEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "GroupEntity{" + "id=" + id + ", name=" + name + ", version=" +
                version + '}';
    }

}
