package com.g5.businesslogic.user;

import com.g5.businesslogic.group.GroupName;
import com.g5.entities.EntityClassHelperLocal;
import com.g5.entities.EntityFactoryLocal;
import com.g5.security.HashGenerator;
import com.g5.security.SaltGenerator;
import com.g5.types.Group;
import com.g5.types.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class InitializeUsers implements InitializeUsersLocal {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;
    @Inject
    private EntityClassHelperLocal entityClassHelper;
    @Inject
    private EntityFactoryLocal entityFactory;

    @Override
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void execute() {
        List<? extends User> users = entityManager.createNamedQuery(
                "User.findAll", entityClassHelper.getUserClass()).setMaxResults(
                        1).getResultList();

        if (users.isEmpty()) {
            Group administratorsGroup = entityManager.createNamedQuery(
                    "Group.findByName", entityClassHelper.getGroupClass()).
                    setParameter("name", GroupName.ADMINISTRATORS.toString()).
                    getSingleResult();

            User user = entityFactory.createUser();
            user.getGroups().add(administratorsGroup);
            user.setUsername(ADMIN_USERNAME);
            user.setSalt(SaltGenerator.generateSalt());
            user.setHash(HashGenerator.generateHash(ADMIN_PASSWORD,
                    user.getSalt()));

            entityManager.persist(user);
        }
    }

}
