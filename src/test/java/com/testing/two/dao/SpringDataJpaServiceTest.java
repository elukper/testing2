package com.testing.two.dao;

import com.testing.two.application.model.BaseEntity;
import com.testing.two.application.service.dao.DaoService;
import com.testing.two.application.service.dao.jpa.SpringDataJpaService;
import com.testing.two.config.profiles.Profiles;
import com.testing.two.config.rootconfig.RootConfig;
import com.testing.two.config.rootconfig.database.SpringDataJpaConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class,SpringDataJpaConfig.class})
@ActiveProfiles(Profiles.TEST)
public class SpringDataJpaServiceTest {
    @Autowired
    DaoService springDataJpaService;

    @Test
    public void getAndSwapFirstAndLastName_StoreAndFetchBaseEntity_FirstAndLastNameSwapped() {
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setFirstName("Luka");
        baseEntity.setLastName("Perkovic");
        baseEntity.setCreateDate(new Date());

        Integer id = springDataJpaService.saveBaseEntity(baseEntity);

        baseEntity = springDataJpaService.getBaseEntity(id);

        assertThat(baseEntity.getFirstName(),equalTo("Luka"));
        assertThat(baseEntity.getLastName(),equalTo("Perkovic"));
    }
}
