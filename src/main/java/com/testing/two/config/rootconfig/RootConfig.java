package com.testing.two.config.rootconfig;

import com.testing.two.config.profiles.Profiles;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for all non-servlet configurations
 * This config will scan all non-serlvet related Components
 * It excludes any Controller, ControllerAdvice, RestControllers
 * It excludes database-related components (those are scanned in {@link com.testing.two.config.rootconfig.database} configurations
 * It excludes security-related components (those are scanned in {@link com.testing.two.config.webconfig.security} configurations
 * Also, here new @Bean configs are defined for non-servlet related Components (e.g. DataSource)
 *
 * The @EnableScheduling is used to enable Scheduling mechanisms and Cron jobs across the entire application
 * The @EnableAsync is used to enable Spring thread mechanisms, invoked via the @Async annotation
 */
@Configuration
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = {"com.testing.two.application","com.testing.two.config.postinitalization"},
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class, ControllerAdvice.class, RestController.class}),
            @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.testing\\.two\\.application\\.repositories..*"),
            @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.testing\\.two\\.application\\.service\\.dao..*"),
            @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.testing\\.two\\.application\\.service\\.authentication..*")
    }
)
public class RootConfig {
    @Autowired
    Environment env;

    //region Property placeholder configurer

    //Configuration for .properties files per Profile, which can be accessed via @Value annotation
    @Bean
    @Profile(Profiles.DEV)
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurerDev()
    {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setLocations(new ClassPathResource("application-dev.properties"));
        return propertySourcesPlaceholderConfigurer;
    }

    @Bean
    @Profile(Profiles.PROD)
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurerProd()
    {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setLocations(new ClassPathResource("application-prod.properties"));
        return propertySourcesPlaceholderConfigurer;
    }

    @Bean
    @Profile(Profiles.TEST)
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurerTest()
    {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setLocations(new ClassPathResource("application-test.properties"));
        return propertySourcesPlaceholderConfigurer;
    }
    //endregion

    //region Config for REST Template

    //This configures a REST client and as such it is not part of the Spring MVC, therefore not part of the ServletConfig
    //Configuration shows how to configure two RestTemplates with different authentications, but common parameters such as timeout
    @Bean(name = "basicRestTemplate")
    RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(getCommonClientHttpRequestFactory());
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("username1","password1"));
        return restTemplate;
    }

    @Bean(name = "anotherBasicRestTemplate")
    RestTemplate getAnotherRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(getCommonClientHttpRequestFactory());
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("username2","password2"));
        return restTemplate;
    }

    private ClientHttpRequestFactory getCommonClientHttpRequestFactory() {
        //TODO implement via parameters
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(50000)
                .setSocketTimeout(50000)
                .setConnectionRequestTimeout(50000)
                .build();
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .build();
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }
    //endregion

    //region Config for Spring Async (threads) and Scheduling

    /**
     * The ThreadPoolTaskScheduler is used by Spring to execute any methods annotated with @Scheduled, as well as any Cron jobs
     * Since Scheduling is Spring-based, it will have
     * @return newly created ThreadPoolTaskScheduler bean
     */
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler
                = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix(
                "ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }

    /**
     * This initializes a bean that implements the {@link TaskExecutor} interface and is used by Spring for creating new threads</br>
     * Anytime a method annotated with @Async is invoked, Spring will use the {@link TaskExecutor} to create a new thread and run the method there</br>
     * Here, {@link ThreadPoolTaskExecutor} will be created and returned
     * @return
     */
    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("WatchService");
        executor.initialize();
        return executor;
    }
    //endregion
}
