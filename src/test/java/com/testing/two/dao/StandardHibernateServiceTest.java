package com.testing.two.dao;

import com.testing.two.application.model.BaseEntity;
import com.testing.two.application.service.dao.DaoService;
import com.testing.two.application.service.dao.standardhibernate.StandardHibernateService;
import com.testing.two.config.profiles.Profiles;
import com.testing.two.config.rootconfig.RootConfig;
import com.testing.two.config.rootconfig.database.StandardHibernateConfig;
import net.sf.ehcache.CacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

//TODO create separate test class for service level Hibernate
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class,StandardHibernateConfig.class})
@ActiveProfiles(Profiles.TEST)
public class StandardHibernateServiceTest {
    @Autowired
    Environment env;

    @Autowired
    DaoService standardHibernateService;

    @Test
    public void getBaseEntity_standardPersisAction_EntityPersistedAndFetched() {
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setFirstName("Luka");
        baseEntity.setLastName("Perkovic");
        Date date = new Date();
        baseEntity.setCreateDate(date);

        Integer id = standardHibernateService.saveBaseEntity(baseEntity);

        baseEntity = standardHibernateService.getBaseEntity(id);
        assertThat(baseEntity.getFirstName(),equalTo("Luka"));
        assertThat(baseEntity.getLastName(),equalTo("Perkovic"));
        assertThat(baseEntity.getCreateDate(),equalTo(date));
    }

    @Test
    public void getBaseEntity_TestSecondLevelCache_EntityCached() {
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setFirstName("Luka");
        baseEntity.setLastName("Perkovic");
        Date date = new Date();
        baseEntity.setCreateDate(date);

        Integer id = standardHibernateService.saveBaseEntity(baseEntity);

        standardHibernateService.getBaseEntity(id);
        int size = CacheManager.ALL_CACHE_MANAGERS.get(0).getCache("com.testing.two.application.model.BaseEntity").getSize();

        assertThat(size,greaterThan(Integer.valueOf(0)));
    }
}
