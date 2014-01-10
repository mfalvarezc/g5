package com.g5.types;

import java.util.List;

public interface User {

    Long getId();

    void setId(Long id);

    List<Group> getGroups();

    void setGroups(List<Group> groups);

    String getUsername();

    void setUsername(String username);

    String getSalt();

    void setSalt(String salt);

    String getHash();

    void setHash(String hash);

}
