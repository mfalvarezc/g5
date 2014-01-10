g5 - Payment system
===================

- - -

Persistence configuration
-------------------------

The application uses a data source provided by the container to connect to the database. The JNDI name of this data source must be *jdbc/g5*.

Glassfish realm configuration
-----------------------------

1. **Copy the realm jar files to the domain lib**. Copy *g5-security/dist/g5-security.jar* and *g5-glassfish-realm/target/g5-glassfish-realm-1.0.jar* to the domain lib directory (e.g. *$AS_HOME/domains/domain1/lib*).

* **Create a new realm in Glassfish**. In the Admin Console the realm configuration can be found in *Configurations > server-config > Security > Realms*. The new realm must have the following parameters:

    * **Name**: g5-realm
    * **Class Name**: com.g5.security.glassfish.G5Realm.
    * Additional properties:
        - **jaas-context**: g5Realm
        - **data-source-jndi**: JNDI of the data source (JDBC resource) used by the application (i.e. *jdbc/g5*).
        - **user-table**: table used to store user information (i.e. *USER_*).
        - **user-pk-column**: primary key column of the *user-table* (i.e. *ID*).
        - **username-column**: column used to store the usernames in the *user-table* (i.e. *USERNAME*).
        - **salt-column**: column used to store the password salt in the *user-table* (i.e. *SALT*).
        - **hash-column**: column used to store the password hash in the *user-table* (i.e. *HASH_*).
        - **group-table**: table used to store group information (i.e. *GROUP_*).
        - **group-pk-column**: primary key column of the *group-table* (i.e. *ID*).
        - **group-name-column**: column used to store the group name in the *group-table* (i.e. *NAME*).
        - **group-user-table**: join table between groups and users (i.e. *GROUP_USER*).
        - **user-fk-column**: user foreign key column in the *group-user-table* (i.e. *USER_ID*).
        - **group-fk-column**: group foreign key column in the *group-user-table* (i.e. *GROUP_ID*).

* **Edit the domain *login.conf* file**. Append the following realm and login module configuration to the *login.conf* file (e.g. *$AS_HOME/domains/domain1/config/login.conf*):

        g5Realm {
            com.g5.security.glassfish.G5LoginModule required;
        };`

web-service-client project configuration
----------------------------------------

This project requires the *cacerts.jks* domain file to establish an SSL connection with the server and use WS-Security. This file can be found in the domain config directory (e.g. *$AS_HOME/domains/domain1/cacerts.jks*) and must be placed in the *g5-web-service-client/src/META-INF* directory.
