package com.g5.businesslogic.group;

import com.g5.entities.EntityClassHelperLocal;
import com.g5.entities.EntityFactoryLocal;
import com.g5.types.Group;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class InitializeGroups implements InitializeGroupsLocal {

    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;
    @Inject
    private EntityFactoryLocal entityFactory;
    @Inject
    private EntityClassHelperLocal entityClassHelper;

    @Override
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void execute() {
        List<? extends Group> groups = entityManager.createNamedQuery(
                "Group.findAll", entityClassHelper.getGroupClass()).
                setMaxResults(1).
                getResultList();

        if (groups.isEmpty()) {
            for (GroupName groupName : GroupName.values()) {
                Group group = entityFactory.createGroup();
                group.setName(groupName.toString());
                entityManager.persist(group);
            }
        }
    }

}
