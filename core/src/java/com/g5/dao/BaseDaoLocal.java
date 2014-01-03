package com.g5.dao;

import javax.persistence.LockModeType;

public interface BaseDaoLocal<T> {

    public T find(final long id);

    public T find(final long id, final LockModeType lockModeType);

    public void lock(final T entity, final LockModeType lockModeType);

    public T merge(final T entity);

    public void persist(final T entity);

}
