package com.testing.two.config.postinitalization;

import com.testing.two.application.model.authentication.Role;
import com.testing.two.application.model.authentication.User;
import com.testing.two.application.repositories.authentication.jpa.RoleRepository;
import com.testing.two.application.repositories.authentication.jpa.UserRepository;
import com.testing.two.application.service.watchservice.PartnerPropertyResolver;
import com.testing.two.application.service.watchservice.WatchServiceExecution;
import com.testing.two.config.webconfig.security.SecurityConfigRemoteDba;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Component used to trigger certain actions after the application has been booted</br>
 * Implements the {@link ApplicationListener} interface, with the {@link ContextRefreshedEvent} generic</br>
 * The {@link ContextRefreshedEvent} is an event that is fired once the Spring application context is booted and/or refreshed.
 * When that happens, the code in {@link #onApplicationEvent(ContextRefreshedEvent)} execute
 */
@Component
public class PostInitializationExecutor implements ApplicationListener<ContextRefreshedEvent> {
    private boolean initializationDone = false;

    @Autowired
    WatchServiceExecution watchServiceExecution;

    @Autowired
    PartnerPropertyResolver partnerPropertyResolver;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Code in this method will execute once the {@link ContextRefreshedEvent} triggers
     * @param event The triggered event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(!initializationDone) {
            startWatchService();
            initializeDefaultCredentials();
            initializationDone = true;

        }
    }

    //region WatchService startup
    private void startWatchService() {
        System.out.println("About to start watch service");
        watchServiceExecution.startWatchService();
    }
    //endregion

    //region Initialize default user credentials
    @Transactional
    protected void initializeDefaultCredentials() {
        //First check if ROLE_ADMIN exists
        Role role = roleRepository.findByName(SecurityConfigRemoteDba.DEFAULT_ADMIN_ROLE);

        if(role == null) {
            //If not, create the role and create the admin user (if role doesn't exist, neither does the admin user)
            role = createAdminRole();
            createDefaultAdminUser(role);
        } else {
            //Otherwise, check if admin user exists. If no, create it. Admin user must have username and pass admin/admin and role ROLE_ADMIN
            User user = userRepository.findByUsername(SecurityConfigRemoteDba.DEFAULT_ADMIN_USERNAME);
            if(user == null) {
                createDefaultAdminUser(role);
            }
        }
    }

    @Transactional
    protected Role createAdminRole() {
        Role role = new Role();
        role.setName(SecurityConfigRemoteDba.DEFAULT_ADMIN_ROLE);
        roleRepository.save(role);

        return role;
    }

    @Transactional
    protected void createDefaultAdminUser(Role role) {
        User adminUser = new User();
        adminUser.setUsername(SecurityConfigRemoteDba.DEFAULT_ADMIN_USERNAME);
        adminUser.setPassword(SecurityConfigRemoteDba.DEFAULT_ADMIN_PASS);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        adminUser.setRoles(roles);

        userRepository.save(adminUser);
    }
    //endregion
}
