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
    private final DatabaseDescriptor databaseDescriptor;
    private final String username;

    public GetGroupNames(DatabaseHelper connectionFactory,
            DatabaseDescriptor databaseDescriptor, String username) {
        this.databaseHelper = connectionFactory;
        this.databaseDescriptor = databaseDescriptor;
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
        final String groupColumn = databaseDescriptor.getGroupColumn();
        final String groupTable = databaseDescriptor.getGroupTable();
        final String usernameColumn = databaseDescriptor.getUsernameColumn();

        return "SELECT " + groupColumn + " FROM " + groupTable + " WHERE " +
                usernameColumn + " = ?";
    }

}
