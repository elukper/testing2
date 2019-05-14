package com.testing.two.config.webconfig.security;

import com.testing.two.config.profiles.Profiles;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * This configuration is based on in-memory authentication
 *
 * Specifies the Spring Security configuration and extends the {@link WebSecurityConfigurerAdapter} to initialize it
 * It must be marked with {@link Configuration} and {@link EnableWebSecurity} annotations
 * The class itself must be registered with either {@link AbstractAnnotationConfigDispatcherServletInitializer}  or {@link org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer} (if it's a standalone, non-MVC app)
 *
 * The default configuration (just extending {@link WebSecurityConfigurerAdapter}) locks down the application completely - all users must authenticate via a form and there is no user registry configured (so, no one can actually register)
 * The default configuration should be overriden one way or another, by overriding the three config methods:</br>
 * {@link WebSecurityConfigurerAdapter#configure(AuthenticationManagerBuilder)} - configures the user credentials repository with several options: in-memory, LDAP, remote database
 */
@Configuration
@EnableWebSecurity
@Profile({Profiles.TEST})
public class SecurityConfigInMemory extends WebSecurityConfigurerAdapter {

    /**
     * This method defines how and where user credentials are stored for authentication, with several options to choose from: in-memory, LDAP, remote database</br>
     * NOTE: by default, no user storing is configured, therefore, no user can authenticate</br>
     * The {@link AuthenticationManagerBuilder} is used to easily specify the repository type that's used, as well as any config details
     *
     * In this implementation, the {@link AuthenticationManagerBuilder} is configured with an in-memory database used for authentication. It is also configured with entries that will be populated once the in-memory db is created.
     * This is springs out-of-the-box in memory db used for authentication purposes only
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN");
        //the .roles() method will take the given string and call the .authorities() method. The .authorities() is the one that actually sets the role for the user
        //the .roles() will just add a "ROLE_" prefix to the provided string and call the .authorities() methods with the prefixed String, e.g. .authorities("ROLE_USER")
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SecurityWebInitializer.configureHttpSecurity(http);
    }
}
