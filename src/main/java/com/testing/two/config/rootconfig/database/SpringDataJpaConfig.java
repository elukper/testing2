package com.testing.two.config.rootconfig.database;

import com.testing.two.application.repositories.jpa.SpringDataJpaBaseRepository;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * This is a configuration for Spring Data JPA</br>
 * It provides configuration for the {@link LocalContainerEntityManagerFactoryBean}, as well as {@link DataSource} and {@link JpaVendorAdapter}, used by the {@link LocalContainerEntityManagerFactoryBean}.</br>
 * It also provides configuration for the {@link JpaTransactionManager}, which enables Transactional management
 * Repository example {@link SpringDataJpaBaseRepository}
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.testing.two.application.repositories.jpa"})
@Import(DataSourceConfig.class)
@ComponentScan(basePackages = {"com.testing.two.application.repositories.jpa","com.testing.two.application.service.dao.jpa"})
public class SpringDataJpaConfig {
    /**
     * Creates a {@link JpaTransactionManager} {@link Bean}</br>
     * It is used to enable the use of declarative transactions via the {@link org.springframework.transaction.annotation.Transactional} annotation, which utilizes Spring AOP</br>
     * Spring will have pointcuts before and after the invocation of the method marked with {@link org.springframework.transaction.annotation.Transactional}</br>
     * The "before" pointcut will have the {@link javax.persistence.EntityManager} (created by the {@link LocalContainerEntityManagerFactoryBean} which the {@link JpaTransactionManager} references) create a new {@link org.hibernate.Transaction} and invoke {@link Transaction#begin()}</br>
     * The "after" pointcut will invoke {@link org.hibernate.Transaction#commit()}. It will also handle any exceptions that might occur. Handing is done by invoking {@link org.hibernate.Transaction#rollback()}</br>
     * The {@link org.springframework.transaction.annotation.Transactional} annotation is used on the {@link org.springframework.stereotype.Service} level, not {@link org.springframework.stereotype.Repository} level
     * @param entityManagerFactory
     * @return
     */
    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    /**
     * Creates a {@link LocalContainerEntityManagerFactoryBean} that is used by JPA to provide {@link javax.persistence.EntityManager}s</br>
     * Normally in Java J2EE, the container would be responsible for providing {@link javax.persistence.EntityManager}s (alternatively, for {@link org.springframework.orm.jpa.LocalEntityManagerFactoryBean}, the application would be responsible)</br>
     * With Spring Data, Spring will handle all things related to providing {@link javax.persistence.EntityManager}
     * @param dataSource The {@link DataSource} which defines connection to the database
     * @param jpaVendorAdapter The {@link JpaVendorAdapter} which adapts the {@link javax.persistence.EntityManager} to the specific vendor (e.g. {@link HibernateJpaVendorAdapter})
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter, @Qualifier("hibernateProperties") Properties properties) {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource);
        emfb.setJpaVendorAdapter(jpaVendorAdapter);
        emfb.setJpaProperties(properties);
        emfb.setPackagesToScan("com.testing.two.application.model");
        return emfb;
    }

    /**
     * Creates a {@link HibernateJpaVendorAdapter} for {@link Database#H2}
     * @return
     */
    //TODO create different JpaVendorAdapter via Profiles
    @Bean
    public JpaVendorAdapter jpaVendorAdapter(@Qualifier("hibernateProperties") HibernateProperties properties) {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(properties.getJpaDatabaseType());
        //SQL queries will be shown in the log
        adapter.setShowSql(true);
        //Schema generaton will be done by liquibase
        adapter.setGenerateDdl(false);
        adapter.setDatabasePlatform(properties.getProperty(HibernateProperties.HIBERNATE_DIALECT_PARAM));
        return adapter;
    }
}
