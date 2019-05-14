package com.testing.two.application.service.dao.jpa;

import com.testing.two.application.model.AnotherEntity;
import com.testing.two.application.model.BaseEntity;
import com.testing.two.application.repositories.jpa.SpringDataJpaAnotherRepository;
import com.testing.two.application.repositories.jpa.SpringDataJpaBaseRepository;
import com.testing.two.application.service.dao.DaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SpringDataJpaService implements DaoService{
    @Autowired
    SpringDataJpaBaseRepository repository;

    @Autowired
    SpringDataJpaAnotherRepository anotherRepository;

    @Override
    public BaseEntity getBaseEntity(int id) {
        return repository.findOne(id);
    }

    @Override
    public AnotherEntity getAnotherEntity(int id) {
        return anotherRepository.findOne(id);
    }

    @Override
    public Integer saveBaseEntity(BaseEntity baseEntity) {
        return (repository.saveAndFlush(baseEntity)).getId();
    }

    @Override
    public Integer saveAnotherEntity(AnotherEntity anotherEntity) {
        return (anotherRepository.saveAndFlush(anotherEntity)).getId();
    }
}
