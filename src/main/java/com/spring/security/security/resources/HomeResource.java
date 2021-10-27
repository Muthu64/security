package com.spring.security.security.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.security.domain.UserDomain;
import com.spring.security.security.jwt.JwtUtils;
import com.spring.security.security.model.CreateUserRequest;
import com.spring.security.security.model.CreateUserResponse;
import com.spring.security.security.model.LoginRequest;
import com.spring.security.security.model.LoginResponse;
import com.spring.security.security.permission.Permission;
import com.spring.security.security.repository.UserRepository;
import com.spring.security.security.userDetail.MyUserDetailsService;

@RestController
public class HomeResource
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @RequestMapping( "/" )
    public String home()
    {
        return "Hello world";
    }

    @RequestMapping( "/admin" )
    @PreAuthorize( "hasAuthority( '"+Permission.ADMIN_ROLE +"')" )
    public String admin()
    {
        return "Hello admin";
    }

    @RequestMapping( "/user" )
    @PreAuthorize( "hasAuthority( '"+Permission.INSTALLER_ROLE +"')" )
    public String user()
    {
        return "Hello user";
    }

    @RequestMapping( value = "/addUser" )
    public String addUser()
    {
        UserDomain userDomain = new UserDomain( "muthu", "muthu", Arrays.asList( "ADMIN", "INSTALLER" ) );
        this.userRepository.save( userDomain );
        return "user saved successfully";
    }

    @RequestMapping( method = RequestMethod.POST, value = "/createUser" )
    @PreAuthorize( "hasAuthority( '"+Permission.ADMIN_ROLE +"')" )
    public CreateUserResponse createUser( @RequestBody CreateUserRequest createUserRequest )
    {
        UserDomain userDomain = new UserDomain( createUserRequest.getUserName(), createUserRequest.getPassword(), createUserRequest.getRoles() );
        this.userRepository.save( userDomain );
        return new CreateUserResponse();
    }

    @RequestMapping( method = RequestMethod.POST, value = "/login" )
    public LoginResponse login( @RequestBody LoginRequest loginRequest ) throws Exception
    {
        Authentication authenticate = null;
        try
        {
            authenticate = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( loginRequest.getUsername(), loginRequest.getPassword() ) );
        }
        catch( BadCredentialsException e )
        {
            throw new Exception( "Bad credentials" );
        }

        //UserDetails userDetails = myUserDetailsService.loadUserByUsername( loginRequest.getUsername() );
        String token = JwtUtils.generateToken( authenticate.getName() );
        List<String> roles = authenticate.getAuthorities().stream().map( authority ->  authority.getAuthority() ).collect( Collectors.toList());

        return new LoginResponse( token, roles );
    }
}
