package com.testing.two.application.service.authentication;

import com.testing.two.application.model.authentication.User;
import com.testing.two.application.repositories.authentication.jpa.RoleRepository;
import com.testing.two.application.repositories.authentication.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implements the Spring {@link UserDetailsService}
 *
 * {@link UserDetailsService} is the core interface which loads user-specific data.
 * It is used throughout the framework as a user DAO and is the strategy used by the {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}.
 * The interface requires only one read-only method, which simplifies support for new data-access strategies.
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Locates the user based on the username and returns an object that implements Springs {@link UserDetails} interface. It provides all the necessary methods for user and role authentication
     * @param s name of the user
     * @return {@link CustomUserPrincipal} which implements the {@link UserDetails} interface. The returned object will contain a wrapped {@link com.testing.two.application.model.authentication.User} fetched from the database
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);

        if(user == null) {
            throw new UsernameNotFoundException(s);
        }

        return new CustomUserPrincipal(user);
    }
}
