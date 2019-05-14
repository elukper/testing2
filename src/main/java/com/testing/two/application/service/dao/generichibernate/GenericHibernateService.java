package com.testing.two.application.service.dao.generichibernate;

import com.testing.two.application.model.AnotherEntity;
import com.testing.two.application.model.BaseEntity;
import com.testing.two.application.repositories.generichibernate.GenericHibernateRepository;
import com.testing.two.application.service.dao.DaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GenericHibernateService implements DaoService {
    private GenericHibernateRepository<BaseEntity,Integer> baseEntityIntegerGenericHibernateRepository;
    private GenericHibernateRepository<AnotherEntity,Integer> anotherEntityIntegerGenericHibernateRepository;

    @Autowired
    public void setBaseEntityIntegerGenericHibernateRepository(GenericHibernateRepository<BaseEntity, Integer> baseEntityIntegerGenericHibernateRepository) {
        this.baseEntityIntegerGenericHibernateRepository = baseEntityIntegerGenericHibernateRepository;
        this.baseEntityIntegerGenericHibernateRepository.setClazz(BaseEntity.class);
    }

    @Autowired
    public void setAnotherEntityIntegerGenericHibernateRepository(GenericHibernateRepository<AnotherEntity, Integer> anotherEntityIntegerGenericHibernateRepository) {
        this.anotherEntityIntegerGenericHibernateRepository = anotherEntityIntegerGenericHibernateRepository;
        this.anotherEntityIntegerGenericHibernateRepository.setClazz(AnotherEntity.class);
    }

    @Override
    public BaseEntity getBaseEntity(int id) {
        return baseEntityIntegerGenericHibernateRepository.findOne(id);
    }

    @Override
    public Integer saveBaseEntity(BaseEntity baseEntity) {
        return baseEntityIntegerGenericHibernateRepository.save(baseEntity);
    }

    @Override
    public AnotherEntity getAnotherEntity(int id) {
        return anotherEntityIntegerGenericHibernateRepository.findOne(id);
    }

    @Override
    public Integer saveAnotherEntity(AnotherEntity anotherEntity) {
        return anotherEntityIntegerGenericHibernateRepository.save(anotherEntity);
    }
}
