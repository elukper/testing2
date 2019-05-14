package com.testing.two.config.rootconfig.database;

import com.testing.two.application.repositories.standardhibernate.StandardHibernateRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * This is a configuration for the standard Hibernate config in Spring</br>
 * It provides configuration for the necessary {@link DataSource}, {@link HibernateTransactionManager} and {@link org.hibernate.SessionFactory}, via the {@link LocalSessionFactoryBean}</br>
 * It also provides configuration for the {@link HibernateTransactionManager}, which enables Transactional management
 * Repository implementation example is {@link StandardHibernateRepository}</br>
 * This is an alternative to Spring Data JPA, see {@link SpringDataJpaConfig}
 */
@Configuration
@EnableTransactionManagement
@Import(DataSourceConfig.class)
@ComponentScan(basePackages = {"com.testing.two.application.repositories.standardhibernate","com.testing.two.application.service.dao.standardhibernate"})
public class StandardHibernateConfig {

    @Autowired
    Environment env;

    //TODO update properties with Prod db
    /**
     * Creates a {@link HibernateTransactionManager} {@link Bean}</br>
     * It implements the {@link PlatformTransactionManager} interface and is used to enable the use of declarative transactions via the {@link org.springframework.transaction.annotation.Transactional} annotation, which utilizes Spring AOP</br>
     * Spring will have pointcuts before and after the invocation of the method marked with {@link org.springframework.transaction.annotation.Transactional}</br>
     * The "before" pointcut will have the {@link org.hibernate.SessionFactory} (which the {@link PlatformTransactionManager} references) create a new {@link org.hibernate.Session} and create a new {@link org.hibernate.Transaction} from {@link Session#beginTransaction()}</br>
     * The "after" pointcut will invoke {@link Transaction#commit()} and will close the session via {@link Session#close()}. It will also handle any exceptions that might occur. Handing is done by invoking {@link Transaction#rollback()}</br>
     * The {@link org.springframework.transaction.annotation.Transactional} annotation is used on the {@link org.springframework.stereotype.Service} level, not {@link org.springframework.stereotype.Repository} level
     * @return created {@link HibernateTransactionManager}, configured with a {@link org.hibernate.SessionFactory}
     */
    @Bean
    public PlatformTransactionManager hibernateTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);
        return hibernateTransactionManager;
    }

    /**
     * Creates a {@link LocalSessionFactoryBean} {@link Bean}</br>
     * The {@link LocalSessionFactoryBean} is a Spring factory bean for a {@link org.hibernate.SessionFactory}, as it implements {@link org.springframework.beans.factory.FactoryBean}</br>
     * Instead of configuring the {@link org.hibernate.SessionFactory} manually, the {@link LocalSessionFactoryBean} is used to simplify the creation, by invoking the {@link LocalSessionFactoryBean#getObject()}.</br>
     * The {@link LocalSessionFactoryBean} is configured with three parameters: datasource, mappingResources (optional) and hibernateProperties.
     * Also, it's configured with one or more directories where {@link javax.persistence.Entity} objects are configured. These will be provisioned to every {@link org.hibernate.SessionFactory} created by this bean.
     * @return created and configured {@link LocalSessionFactoryBean}
     */
    @Bean
    public LocalSessionFactoryBean getSessionFactory(DataSource dataSource, @Qualifier("hibernateProperties") Properties properties) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        //Configure datasource
        sessionFactory.setDataSource(dataSource);
        //Configure hibernate properties
        sessionFactory.setHibernateProperties(properties);
        //Configure packages to scan
        sessionFactory.setPackagesToScan("com.testing.two.application.model");
        return sessionFactory;
    }
}
