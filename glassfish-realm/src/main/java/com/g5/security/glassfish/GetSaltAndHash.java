package com.g5.security.glassfish;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

public class GetSaltAndHash {

    private final DatabaseHelper databaseHelper;
    private final DatabaseDescription databaseDescriptor;
    private final String username;

    public GetSaltAndHash(DatabaseHelper databaseHelper,
            DatabaseDescription databaseDescriptor, String username) {
        this.databaseHelper = databaseHelper;
        this.databaseDescriptor = databaseDescriptor;
        this.username = username;
    }

    public SaltAndHash execute() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseHelper.createConnection();

            statement = connection.prepareStatement(getQuery());
            statement.setString(1, username);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String salt = resultSet.getString(1);
                String hash = resultSet.getString(2);

                if (resultSet.next()) {
                    throw new IllegalStateException("The username is not unique");
                }

                return new SaltAndHash(salt, hash);
            }

            return null;
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.
                    getMessage(), ex);
            return null;
        } finally {
            databaseHelper.close(connection, statement, resultSet);
        }
    }

    private String getQuery() {
        final String saltColumn = databaseDescriptor.getSaltColumn();
        final String hashColumn = databaseDescriptor.getHashColumn();
        final String userTable = databaseDescriptor.getUserTable();
        final String usernameColumn = databaseDescriptor.getUsernameColumn();

        return "SELECT " + saltColumn + ", " + hashColumn + " FROM " + userTable +
                " WHERE " +
                usernameColumn + " = ?";
    }
}
