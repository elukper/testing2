package com.testing.two.application.repositories.generichibernate;

import com.testing.two.application.model.AbstractPersistable;

import java.io.Serializable;
import java.util.List;

public interface GenericHibernateRepository<T extends AbstractPersistable, I extends Serializable> {
    T findOne(final I id);

    List<T> findAll();

    void create(final T entity);

    void update( T entity );

    void delete(final T entity);

    void deleteById(final I entityId);

    I save(T entity);

    void setClazz( Class< T > clazzToSet );
}
