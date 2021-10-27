package com.spring.security.security.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserDomain
{
    @Id
    private String id;
    private String name;
    private String password;
    private List<String> roles;

    @PersistenceConstructor
    public UserDomain( String name, String password, List<String> roles )
    {
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getPassword()
    {
        return password;
    }

    public List<String> getRoles()
    {
        return roles;
    }
}
