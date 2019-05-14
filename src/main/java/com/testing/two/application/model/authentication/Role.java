package com.testing.two.application.model.authentication;

import com.testing.two.application.model.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "ROLES")
public class Role extends AbstractPersistable {
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<User> getUsers() {
        return Collections.unmodifiableCollection(users);
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
