package com.testing.two.application.service.dao;

import com.testing.two.application.model.AnotherEntity;
import com.testing.two.application.model.BaseEntity;

public interface DaoService {
    BaseEntity getBaseEntity(int id);
    Integer saveBaseEntity(BaseEntity baseEntity);
    AnotherEntity getAnotherEntity(int id);
    Integer saveAnotherEntity(AnotherEntity anotherEntity);
}
