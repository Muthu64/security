package com.spring.security.security.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.spring.security.security.domain.UserDomain;

public class MyUserDetails implements UserDetails
{
    private String username;
    private String password;
    private List<GrantedAuthority> grantedAuthorities;

    public MyUserDetails( UserDomain userDomain )
    {
        Objects.requireNonNull( userDomain, "userDomain must not be null");
        Objects.requireNonNull( userDomain.getName(), "name must not be null");
        Objects.requireNonNull( userDomain.getPassword(), "password must not be null");
        Objects.requireNonNull( userDomain.getRoles(), "roles must not be null");

        this.username = userDomain.getName();
        this.password = userDomain.getPassword();
        this.grantedAuthorities = userDomain.getRoles().stream().map( SimpleGrantedAuthority::new ).collect( Collectors.toList() );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }

    @Override
    public String getUsername()
    {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return "MyUserDetails{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", grantedAuthorities=" + grantedAuthorities + '}';
    }
}
