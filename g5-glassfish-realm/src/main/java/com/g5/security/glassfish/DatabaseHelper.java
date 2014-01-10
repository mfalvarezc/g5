package com.g5.security.glassfish;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseHelper {

    private final String dataSourceJndi;
    private final String dbUser;
    private final String dbPassword;

    public DatabaseHelper(String dataSourceJndi, String dbUser,
            String dbPassword) {
        this.dataSourceJndi = dataSourceJndi;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection createConnection() throws NamingException, SQLException {
        final Context context = new InitialContext();
        final DataSource dataSource = (DataSource) context.lookup(
                dataSourceJndi);
        Connection connection;
        if (dbUser != null && dbPassword != null) {
            connection = dataSource.getConnection(dbUser, dbPassword);
        } else {
            connection = dataSource.getConnection();
        }
        return connection;
    }

    public void close(Connection connection, PreparedStatement statement,
            ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
            }
        }
    }

}
