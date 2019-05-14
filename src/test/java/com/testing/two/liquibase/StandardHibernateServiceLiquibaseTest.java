package com.testing.two.liquibase;

import com.testing.two.application.model.AnotherEntity;
import com.testing.two.application.model.BaseEntity;
import com.testing.two.application.service.dao.DaoService;
import com.testing.two.application.service.dao.standardhibernate.StandardHibernateService;
import com.testing.two.config.profiles.Profiles;
import com.testing.two.config.rootconfig.RootConfig;
import com.testing.two.config.rootconfig.database.StandardHibernateConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Tests database table generation with Liquibase script
 * Config is in liquibase-dev-changeLog.xml file for DEV Profile
 * For DEV profile, Hibernate property hibernate.hbm2ddl.auto should not be set, to avoid conflict with Liquibase
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class,StandardHibernateConfig.class})
@ActiveProfiles(Profiles.DEV)
public class StandardHibernateServiceLiquibaseTest {

    @Autowired
    DaoService standardHibernateService;

    @Test
    @Ignore
    public void save_persistEntityInTableGeneratedByLiquibase_EntityPersistedAndFetched() {
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
    @Ignore
    public void save_persistEntityTableNotGeneratedByLiquibase_EntityPersistanceFailed() {
        boolean persistanceFailed = false;
        AnotherEntity anotherEntity = new AnotherEntity();
        anotherEntity.setNickname("nickname");

        try {
            standardHibernateService.saveAnotherEntity(anotherEntity);
        } catch (Exception e) {
            persistanceFailed = true;
        }

        assertThat(persistanceFailed,equalTo(true));
    }
}
