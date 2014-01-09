package com.g5.security.glassfish;

public class DatabaseDescription {

    private final String userTable;
    private final String userPkColumn;
    private final String usernameColumn;
    private final String saltColumn;
    private final String hashColumn;
    private final String groupUserTable;
    private final String groupTable;
    private final String groupPkColumn;
    private final String groupNameColumn;

    public DatabaseDescription(String userTable, String userPkColumn,
            String usernameColumn, String saltColumn, String hashColumn,
            String groupUserTable, String groupTable, String groupPkColumn,
            String groupNameColumn) {
        this.userTable = userTable;
        this.userPkColumn = userPkColumn;
        this.usernameColumn = usernameColumn;
        this.saltColumn = saltColumn;
        this.hashColumn = hashColumn;
        this.groupUserTable = groupUserTable;
        this.groupTable = groupTable;
        this.groupPkColumn = groupPkColumn;
        this.groupNameColumn = groupNameColumn;
    }

    public String getUserTable() {
        return userTable;
    }

    public String getUserPkColumn() {
        return userPkColumn;
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

    public String getGroupUserTable() {
        return groupUserTable;
    }

    public String getGroupTable() {
        return groupTable;
    }

    public String getGroupPkColumn() {
        return groupPkColumn;
    }

    public String getGroupNameColumn() {
        return groupNameColumn;
    }

}
