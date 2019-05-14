package com.testing.two.application.repositories.standardhibernate;

import com.testing.two.application.model.AbstractPersistable;
import com.testing.two.application.model.AnotherEntity;
import com.testing.two.application.model.BaseEntity;

/**
 * This is a manual implementation of a DAO layer repository
 * It uses {@link org.hibernate.SessionFactory} to open a session and fetch, persist, save or update an {@link javax.persistence.Entity} object
 */
public interface StandardHibernateRepository {

    BaseEntity getBaseEntity(int id);

    Integer persistBaseEntity(BaseEntity baseEntity);

    Integer persistAnotherEntity(AnotherEntity anotherEntity);

    AnotherEntity getAnotherEntity(int id);
}
