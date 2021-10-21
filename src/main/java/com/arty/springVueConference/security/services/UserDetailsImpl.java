package com.arty.springVueConference.security.services;


import java.util.*;

import com.arty.springVueConference.domains.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.arty.springVueConference.domains.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String login;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    private Set<Role> roles;

    public UserDetailsImpl(Long id, String login, String email, String password, Set<Role> roles,
                           Set<SimpleGrantedAuthority> authorities) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().stream().forEach(role -> role.getName().getAuthorities()
                .stream().forEach(authority -> Collections.addAll(authorities, authority)));


        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles(),
                authorities);
    }

    public Set<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}

