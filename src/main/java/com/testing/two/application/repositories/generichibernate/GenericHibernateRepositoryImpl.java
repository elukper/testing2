package com.testing.two.application.repositories.generichibernate;

import com.testing.two.application.model.AbstractPersistable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE )
public class GenericHibernateRepositoryImpl<T extends AbstractPersistable, I extends Serializable> extends AbstractGenericHibernateRepository<T,I> implements GenericHibernateRepository<T,I> {

}
