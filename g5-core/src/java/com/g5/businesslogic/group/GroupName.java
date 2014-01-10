package com.g5.businesslogic.group;

public enum GroupName {

    ADMINISTRATORS("Administrators"),
    CUSTOMERS("Customers");

    private final String name;

    private GroupName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
