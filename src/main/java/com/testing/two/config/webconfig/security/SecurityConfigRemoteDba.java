package com.testing.two.config.webconfig.security;

import com.testing.two.config.profiles.Profiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * This configuration is based on remote DBA configuration
 *
 * Specifies the Spring Security configuration and extends the {@link WebSecurityConfigurerAdapter} to initialize it
 * It must be marked with {@link Configuration} and {@link EnableWebSecurity} annotations
 * The class itself must be registered with either {@link AbstractAnnotationConfigDispatcherServletInitializer}  or {@link org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer} (if it's a standalone, non-MVC app)
 *
 * The default configuration (just extending {@link WebSecurityConfigurerAdapter}) locks down the application completely - all users must authenticate via a form and there is no user registry configured (so, no one can actually register)
 * The default configuration should be overriden one way or another, by overriding the three config methods:</br>
 * {@link WebSecurityConfigurerAdapter#configure(AuthenticationManagerBuilder)} - configures the user credentials repository with several options: in-memory, LDAP, remote database
 *
 * NOTE: ComponentScan should scan components related only to authentication. Make sure these are excluded from {@link com.testing.two.config.rootconfig.RootConfig} scanning as well
 */
@Configuration
@EnableWebSecurity
@Profile({Profiles.PROD,Profiles.DEV})
@EnableJpaRepositories(basePackages = {"com.testing.two.application.repositories.authentication.jpa"})
@ComponentScan(basePackages = {"com.testing.two.application.service.authentication","com.testing.two.application.repositories.authentication.jpa"})
public class SecurityConfigRemoteDba extends WebSecurityConfigurerAdapter {
    private static final int PASSWORD_ENCODER_STRENGTH = 11;
    public static final String DEFAULT_ADMIN_USERNAME = "admin";
    public static final String DEFAULT_ADMIN_PASS = "admin";
    public static final String DEFAULT_ADMIN_ROLE = "ROLE_ADMIN";

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * This method defines how and where user credentials are stored for authentication, with several options to choose from: in-memory, LDAP, remote database</br>
     * NOTE: by default, no user storing is configured, therefore, no user can authenticate</br>
     * The {@link AuthenticationManagerBuilder} is used to easily specify the repository type that's used, as well as any config details
     *
     * In this implementation, the {@link AuthenticationManagerBuilder} is configured with a {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}. It is an implementation of the {@link org.springframework.security.authentication.AuthenticationProvider} which retrieves user details from a {@link org.springframework.security.core.userdetails.UserDetailsService}</br>
     * The {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider} must be created as a new bean, which can be configured in this class, in {@link #daoAuthenticationProvider()}. The {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider} must be supplied with a bean implementing {@link org.springframework.security.core.userdetails.UserDetailsService}.
     * In this case, it will be supplied with {@link com.testing.two.application.service.authentication.UserDetailsServiceImpl}, therefore any custom authentication logic should be implemented there, in the {@link com.testing.two.application.service.authentication.UserDetailsServiceImpl#loadUserByUsername(String)} method
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    /**
     * Creates a {@link DaoAuthenticationProvider} which is assigned to the {@link AuthenticationManagerBuilder} and supplied with {@link com.testing.two.application.service.authentication.UserDetailsServiceImpl} for resolving users
     * @return
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * Password encoder to be used for encoding user passwords in the database
     * @return {@link BCryptPasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(PASSWORD_ENCODER_STRENGTH);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SecurityWebInitializer.configureHttpSecurity(http);
    }
}
