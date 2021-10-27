package com.spring.security.security.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest
{
    private String userName;
    private String password;
    private List<String> roles;

    @JsonCreator
    public CreateUserRequest( @JsonProperty("userName") String userName,
                              @JsonProperty("password") String password,
                              @JsonProperty("roles") List<String> roles )
    {
        Objects.requireNonNull( userName );
        Objects.requireNonNull( password );
        Objects.requireNonNull( roles );

        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }

    public List<String> getRoles()
    {
        return roles;
    }

    @Override
    public String toString()
    {
        return "CreateUserRequest{" + "userName='" + userName + '\'' + ", password='" + password + '\'' + ", roles=" + roles + '}';
    }
}
