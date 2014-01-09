package com.g5.security.glassfish;

import com.sun.appserv.security.AppservRealm;
import com.sun.enterprise.security.auth.realm.BadRealmException;
import com.sun.enterprise.security.auth.realm.NoSuchRealmException;
import java.util.Properties;
import com.sun.enterprise.security.auth.realm.InvalidOperationException;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class G5Realm extends AppservRealm {

    private static final String AUTH_TYPE = "Secure JDBC";

    private DatabaseHelper databaseHelper;
    private DatabaseDescription databaseDescription;

    private static final String DATA_SOURCE_JNDI_PARAM = "data-source-jndi";
    private static final String USER_TABLE_PARAM = "user-table";
    private static final String USER_PK_COLUMN_PARAM = "user-pk-column";
    private static final String USERNAME_COLUMN_PARAM = "username-column";
    private static final String SALT_COLUMN_PARAM = "salt-column";
    private static final String HASH_COLUMN_PARAM = "hash-column";
    private static final String GROUP_USER_TABLE_PARAM = "group-user-table";
    private static final String GROUP_TABLE_PARAM = "group-table";
    private static final String GROUP_PK_COLUMN_PARAM = "group-pk-column";
    private static final String GROUP_NAME_COLUMN_PARAM =
            "group-name-column";

    private static final String DB_USER_PARAM = "db-user";
    private static final String DB_PASSWORD_PARAM = "db-password";

    @Override
    protected void init(Properties properties) throws BadRealmException,
            NoSuchRealmException {
        String dataSourceJndi = getRequiredProperty(properties,
                DATA_SOURCE_JNDI_PARAM);
        String userTable = getRequiredProperty(properties, USER_TABLE_PARAM);
        String userPkColumn = getRequiredProperty(properties,
                USER_PK_COLUMN_PARAM);
        String usernameColumn = getRequiredProperty(properties,
                USERNAME_COLUMN_PARAM);
        String saltColumn = getRequiredProperty(properties, SALT_COLUMN_PARAM);
        String hashColumn = getRequiredProperty(properties, HASH_COLUMN_PARAM);
        String groupUserTable = getRequiredProperty(properties,
                GROUP_USER_TABLE_PARAM);
        String groupTable = getRequiredProperty(properties, GROUP_TABLE_PARAM);
        String groupPkColumn = getRequiredProperty(properties,
                GROUP_PK_COLUMN_PARAM);
        String groupName = getRequiredProperty(properties,
                GROUP_NAME_COLUMN_PARAM);

        String dbUser = getOptionalProperty(properties, DB_USER_PARAM);
        String dbPassword = getOptionalProperty(properties, DB_PASSWORD_PARAM);

        databaseHelper = new DatabaseHelper(dataSourceJndi,
                dbUser, dbPassword);
        databaseDescription = new DatabaseDescription(userTable, userPkColumn,
                usernameColumn, saltColumn, hashColumn, groupUserTable,
                groupTable, groupPkColumn, groupPkColumn);
    }

    private String getRequiredProperty(Properties properties,
            String propertyName)
            throws BadRealmException {
        String property = properties.getProperty(propertyName);

        if (property == null) {
            throw new BadRealmException("The property " + propertyName +
                    " is missing.");
        }

        return property;
    }

    private String getOptionalProperty(Properties properties,
            String propertyName)
            throws BadRealmException {
        return properties.getProperty(propertyName);
    }

    @Override
    public String getAuthType() {
        return AUTH_TYPE;
    }

    @Override
    public Enumeration getGroupNames(String username) throws
            InvalidOperationException, NoSuchUserException {
        List<String> groups = (new GetGroupNames(databaseHelper,
                databaseDescription, username)).
                execute();

        if (groups != null) {
            return Collections.enumeration(groups);
        } else {
            return Collections.emptyEnumeration();
        }
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public DatabaseDescription getDatabaseDescriptor() {
        return databaseDescription;
    }

}
