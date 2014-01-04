package com.g5.dao;

import java.lang.reflect.ParameterizedType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

public abstract class AbstractBaseDao<I, E extends I> implements BaseDaoLocal<I> {

    @PersistenceContext(unitName = "g5-jta")
    protected EntityManager entityManager;
    protected Class<E> entityClass;

    public AbstractBaseDao() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Override
    public I find(long id) {
        System.out.println("AbstractBaseDao.find()");
        return entityManager.find(entityClass, id);
    }

    @Override
    public I find(long id, LockModeType lockModeType) {
        return entityManager.find(entityClass, id, lockModeType);
    }

    @Override
    public void lock(I entity, LockModeType lockModeType) {
        entityManager.lock(entity, lockModeType);
    }

    @Override
    public I merge(I entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void persist(I entity) {
        entityManager.persist(entity);
    }

}
