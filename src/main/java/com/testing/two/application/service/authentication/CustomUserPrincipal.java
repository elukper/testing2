package com.testing.two.application.service.authentication;

import com.testing.two.application.model.authentication.Role;
import com.testing.two.application.model.authentication.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomUserPrincipal implements UserDetails {

    private User user;
    List<GrantedAuthority> grantedAuthorities;

    public CustomUserPrincipal(User user) {
        this.user = user;
        grantedAuthorities = new ArrayList<>();

        for(Role roles : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(roles.getName()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableList(grantedAuthorities);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
