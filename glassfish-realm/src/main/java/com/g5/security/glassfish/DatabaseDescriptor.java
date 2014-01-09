package com.g5.security.glassfish;

public class DatabaseDescriptor {

    private final String userTable;
    private final String usernameColumn;
    private final String saltColumn;
    private final String hashColumn;
    private final String groupTable;
    private final String groupColumn;

    public DatabaseDescriptor(String userTable, String usernameColumn,
            String saltColumn, String hashColumn, String groupTable,
            String groupColumn) {
        this.userTable = userTable;
        this.usernameColumn = usernameColumn;
        this.saltColumn = saltColumn;
        this.hashColumn = hashColumn;
        this.groupTable = groupTable;
        this.groupColumn = groupColumn;
    }

    public String getUserTable() {
        return userTable;
    }

    public String getUsernameColumn() {
        return usernameColumn;
    }

    public String getSaltColumn() {
        return saltColumn;
    }

    public String getHashColumn() {
        return hashColumn;
    }

    public String getGroupTable() {
        return groupTable;
    }

    public String getGroupColumn() {
        return groupColumn;
    }

}
