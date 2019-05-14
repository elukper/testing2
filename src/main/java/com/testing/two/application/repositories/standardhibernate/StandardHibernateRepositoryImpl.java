package com.testing.two.application.repositories.standardhibernate;

import com.testing.two.application.model.AnotherEntity;
import com.testing.two.application.model.BaseEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StandardHibernateRepositoryImpl implements StandardHibernateRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public BaseEntity getBaseEntity(int id) {
        return sessionFactory.getCurrentSession().get(BaseEntity.class, id);
    }

    @Override
    public Integer persistBaseEntity(BaseEntity baseEntity) {
        return (Integer) sessionFactory.getCurrentSession().save(baseEntity);
    }

    @Override
    public Integer persistAnotherEntity(AnotherEntity anotherEntity) {
        return (Integer) sessionFactory.getCurrentSession().save(anotherEntity);
    }

    @Override
    public AnotherEntity getAnotherEntity(int id) {
        return sessionFactory.getCurrentSession().get(AnotherEntity.class,id);
    }
}
