package com.spring.security.security.model;

import java.util.List;
import java.util.Objects;

public class LoginResponse
{
    private String token;
    private List<String> roles;

    public LoginResponse( String token, List<String> roles )
    {
        Objects.requireNonNull( token, "token must not be null" );
        Objects.requireNonNull( roles, "roles must not be null" );

        this.token = token;
        this.roles = roles;
    }

    public String getToken()
    {
        return token;
    }

    public List<String> getRoles()
    {
        return roles;
    }
}
