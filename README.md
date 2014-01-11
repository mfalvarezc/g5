g5 - Payment system
===================

Structure
---------

The g5 payment system is contains the following projects:

* **g5-common**. NetBeans Java library project to define basic types and exceptions. The types defined are:

    - **User**
    - **Group**
    - **Customer**
    - **Account** 
    - **Transaction**
    - **Payment**

    And there is only one exception: NotEnoughFundsException.

* **g5-security**. NetBeans Java Library project to define salt generation and hash generation used in the authentication process.

* **g5-core**. NetBeans EJB module project to define the system business logic. Uses JPA, and maps the types defined in *g5-common* to JPA entities. The business logic is implemented using the command pattern. Every command represents a business logic unit (e.g. create customer, create account, deposit, withdraw, etc.). These commands are exposed using sesion facades (AccountFacade, CustomerFacade, PaymentFacade and TransactionFacade). The commands and session facades are implemented using stateless session beans.

    This project uses a JTA (Java Transaction API) data source, CMT (container managed transactions) and Bean Validation 1.1.

* **g5-web-services**. NetBeans Java web application project to expose the session facade beans of the *g5-core* project as web-services. These web services require TLS (Transport Layer Security) and WS-Security (Username authentication with symmetric key). The WS-Security is built on the Metro web service stack and uses the Basic128 algorithm suite. The key store and trust store used are the default stores provided by a Glassfish domain.

* **g5-ear**. NetBeans entreprise application project to package the *g5-core* and *g5-web-services* projects as an enterprise application. Defines the application security realm and group-to-security-role mappings.

* **g5-glassfish-realm**. Maven project to define a custom Glassfish realm and login module to authenticate users against the model of users and groups used by the system.

* **g5-web-service-client**. NetBeans Java application to test the web services provided by *g5-web-services* and their security features.

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
