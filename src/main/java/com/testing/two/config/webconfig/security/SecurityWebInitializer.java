package com.testing.two.config.webconfig.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * An implementation of a secondary WebApplicationInitializer, one that will initialize Spring Security
 */
public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {
    //Since this is a Spring MVC application, it is initialized via the AbstractAnnotationConfigDispatcherServletInitializer, so there is no need for any additional configuration here
    //The AbstractSecurityWebApplicationInitializer will initialize Spring Security, register a DelegatingFilterProxy and the necessary Filter Chains
    //The Spring Security Configuration class must be registered with the main WebApplicationInitializer (AbstractAnnotationConfigDispatcherServletInitializer) and should be registered under the RootConfig

    //For details, see: https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#abstractsecuritywebapplicationinitializer-with-spring-mvc

    //If this was implemented without any previous Spring initializations, then we would have to register the Spring Security configuration here
    /*
    public SecurityWebInitializer() {
        super(SecurityConfig.class);
    }
    */

    /**
     * Just a static utility method for configuring HttpSecurity
     * @param http HttpSecurity to be configured
     * @throws Exception whenever one occurs
     */
    public static void configureHttpSecurity(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .rememberMe().disable() //by default, rememberMe is enabled and will store the user's cookie for two weeks. Cookie is username+password+role+key hashed with MD5. by default, key is "SpringSecured", but can be changed with .key(String key). Cookie storage duration can be changed with .tokenValiditySeconds(int seconds)
                .authorizeRequests()
                .antMatchers("/jackson/partner").hasRole("USER")
                .antMatchers("/login*").permitAll()     //to allow access to our custom login webpage
                .anyRequest().authenticated()
                .and()
                .requiresChannel()
                //.antMatchers("/jackson/partner").requiresSecure()
                .anyRequest().requiresInsecure()
                .and()
                .formLogin()
                .loginPage("/login")    //specifies custom login page
                .defaultSuccessUrl("/index",true)
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login")
                .and().csrf().disable(); //Disables CSRF protection. Find a way to enable it
    }
}
