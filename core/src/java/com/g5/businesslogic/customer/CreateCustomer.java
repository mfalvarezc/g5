package com.g5.businesslogic.customer;

import com.g5.businesslogic.constraints.Password;
import com.g5.businesslogic.constraints.Username;
import com.g5.businesslogic.groups.GroupName;
import com.g5.entities.EntityClassHelperLocal;
import com.g5.entities.EntityFactoryLocal;
import com.g5.security.HashGenerator;
import com.g5.security.SaltGenerator;
import com.g5.types.Customer;
import com.g5.types.Group;
import com.g5.types.User;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CreateCustomer implements CreateCustomerLocal {
    
    @PersistenceContext(unitName = "g5-jta")
    private EntityManager entityManager;
    @Inject
    private EntityClassHelperLocal entityClassHelper;
    @Inject
    private EntityFactoryLocal entityFactory;
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Customer execute(@Username String username, @Password String password) {
        Group group = entityManager.createNamedQuery("Group.findByName",
                entityClassHelper.getGroupClass()).setParameter("name",
                        GroupName.CUSTOMERS.toString()).getSingleResult();
        
        User user = entityFactory.createUser();
        
        user.setUsername(username);
        user.setSalt(SaltGenerator.generateSalt());
        user.setHash(HashGenerator.generateHash(password,
                user.getSalt()));
        user.getGroups().add(group);
        
        entityManager.persist(user);
        
        Customer customer = entityFactory.createCustomer();
        customer.setUser(user);
        
        entityManager.persist(customer);
        
        return customer;
    }
    
}
