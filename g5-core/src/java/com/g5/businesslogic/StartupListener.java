package com.g5.businesslogic;

import com.g5.businesslogic.group.InitializeGroupsLocal;
import com.g5.businesslogic.user.InitializeUsersLocal;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Singleton
@LocalBean
@Startup
public class StartupListener {

    @Inject
    private InitializeGroupsLocal initializeGroups;
    @Inject
    private InitializeUsersLocal initializeUsers;

    @PostConstruct
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void initialize() {
        initializeGroups.execute();
        initializeUsers.execute();
    }

}
