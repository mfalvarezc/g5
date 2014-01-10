package com.g5.security.glassfish;

import com.g5.security.HashGenerator;
import com.sun.appserv.security.AppservPasswordLoginModule;
import com.sun.enterprise.security.auth.realm.Realm;
import java.util.List;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

public class G5LoginModule extends AppservPasswordLoginModule {

    @Override
    protected void authenticateUser() throws LoginException {
        Realm realm = getCurrentRealm();

        if (!(realm instanceof G5Realm)) {
            throw new FailedLoginException("Incorrect realm");
        }

        G5Realm g5Realm = (G5Realm) realm;

        DatabaseHelper databaseHelper = g5Realm.getDatabaseHelper();
        DatabaseDescription databaseDescriptor = g5Realm.getDatabaseDescriptor();

        SaltAndHash saltAndHash =
                (new GetSaltAndHash(databaseHelper, databaseDescriptor,
                        getUsername()
                )).
                execute();

        if (saltAndHash != null) {
            String saltFromDb = saltAndHash.getSalt();
            String hashFromDb = saltAndHash.getHash();

            String hash = HashGenerator.generateHash(new String(
                    getPasswordChar()), saltFromDb);

            if (hash.equals(hashFromDb)) {
                List<String> groups = (new GetGroupNames(databaseHelper,
                        databaseDescriptor, getUsername())).execute();

                if (groups != null && !groups.isEmpty()) {
                    String[] groupsArray = new String[groups.size()];
                    commitUserAuthentication(groups.toArray(groupsArray));
                }
            } else {
                throw new FailedLoginException("Incorrect password.");
            }
        } else {
            throw new FailedLoginException("The user does not exists.");
        }
    }
}
