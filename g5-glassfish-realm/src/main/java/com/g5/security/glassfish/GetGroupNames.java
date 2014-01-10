package com.g5.security.glassfish;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

public class GetGroupNames {

    private final DatabaseHelper databaseHelper;
    private final DatabaseDescription databaseDescription;
    private final String username;

    public GetGroupNames(DatabaseHelper connectionFactory,
            DatabaseDescription databaseDescription, String username) {
        this.databaseHelper = connectionFactory;
        this.databaseDescription = databaseDescription;
        this.username = username;
    }

    public List<String> execute() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseHelper.createConnection();

            statement = connection.prepareStatement(getQuery());
            statement.setString(1, username);

            resultSet = statement.executeQuery();

            final List<String> groups = new ArrayList<>();

            while (resultSet.next()) {
                groups.add(resultSet.getString(1));
            }

            return groups;
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.
                    getMessage(), ex);
            return null;
        } finally {
            databaseHelper.close(connection, statement, resultSet);
        }
    }

    private String getQuery() {
        final String groupTable = databaseDescription.getGroupTable();
        final String groupNameColumn = databaseDescription.getGroupNameColumn();
        final String groupPkColumn = databaseDescription.getGroupPkColumn();
        final String groupUserTable = databaseDescription.getGroupUserTable();
        final String userFkColumn = databaseDescription.getUserFkColumn();
        final String groupFkColumn = databaseDescription.getGroupFkColumn();
        final String userTable = databaseDescription.getUserTable();
        final String userPkColumn = databaseDescription.getUserPkColumn();
        final String usernameColumn = databaseDescription.getUsernameColumn();

        return "SELECT g." + groupNameColumn + " FROM " + groupTable +
                " g INNER JOIN " + groupUserTable + " gu ON g." + groupPkColumn +
                " = gu." + groupFkColumn + " INNER JOIN " + userTable +
                " u ON gu." + userFkColumn + " = u." + userPkColumn +
                " WHERE u." +
                usernameColumn + " = ?";
    }

}
