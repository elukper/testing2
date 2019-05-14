package com.testing.two.dao;

import com.testing.two.application.model.BaseEntity;
import com.testing.two.application.service.dao.DaoService;
import com.testing.two.config.profiles.Profiles;
import com.testing.two.config.rootconfig.RootConfig;
import com.testing.two.config.rootconfig.database.GenericHibernateConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, GenericHibernateConfig.class})
@ActiveProfiles(Profiles.TEST)
public class GenericHibernateServiceTest {
    @Autowired
    DaoService genericHibernateService;

    @Test
    public void getBaseEntity_genericPersisAction_EntityPersistedAndFetched() {
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setFirstName("Luka");
        baseEntity.setLastName("Perkovic");
        Date date = new Date();
        baseEntity.setCreateDate(date);

        Integer id = genericHibernateService.saveBaseEntity(baseEntity);

        baseEntity = genericHibernateService.getBaseEntity(id);
        assertThat(baseEntity.getFirstName(),equalTo("Luka"));
        assertThat(baseEntity.getLastName(),equalTo("Perkovic"));
        assertThat(baseEntity.getCreateDate(),equalTo(date));
    }
}
