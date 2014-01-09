package com.g5.types;

public interface Customer {

    Long getId();

    void setId(Long id);

    User getUser();

    void setUser(User user);

    boolean isEnabled();

    void setEnabled(boolean enabled);

}
