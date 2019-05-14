package com.testing.two.application.service.dao.standardhibernate;

import com.testing.two.application.model.AnotherEntity;
import com.testing.two.application.model.BaseEntity;
import com.testing.two.application.repositories.standardhibernate.StandardHibernateRepository;
import com.testing.two.application.service.dao.DaoService;
import com.testing.two.config.rootconfig.database.StandardHibernateConfig;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a DAO layer Service
 * It contains {@link org.springframework.stereotype.Repository} beans which are used for ORM database access
 * It is maked with {@link Transactional}, see {@link StandardHibernateConfig#hibernateTransactionManager(SessionFactory)} ()} for details
 */
@Service
@Transactional
public class StandardHibernateService implements DaoService {
    @Autowired
    private StandardHibernateRepository manualRepository;

    public BaseEntity getBaseEntity(int id) {
        return manualRepository.getBaseEntity(id);
    }

    @Override
    public Integer saveBaseEntity(BaseEntity baseEntity) {
        return manualRepository.persistBaseEntity(baseEntity);
    }

    @Override
    public AnotherEntity getAnotherEntity(int id) {
        return manualRepository.getAnotherEntity(id);
    }

    @Override
    public Integer saveAnotherEntity(AnotherEntity anotherEntity) {
        return manualRepository.persistAnotherEntity(anotherEntity);
    }
}
