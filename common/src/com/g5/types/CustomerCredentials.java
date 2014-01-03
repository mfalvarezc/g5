package com.g5.types;

public interface CustomerCredentials {

    public Long getId();

    public void setId(Long id);

    public Customer getCustomer();

    public void setCustomer(Customer customer);

    public String getUsername();

    public void setUsername(String username);

    public String getSalt();

    public void setSalt(String salt);

    public String getKey();

    public void setKey(String key);

}
