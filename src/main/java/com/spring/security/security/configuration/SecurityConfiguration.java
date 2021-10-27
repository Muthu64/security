package com.spring.security.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityFilter securityFilter;

    //Authentication process
    @Override
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception
    {
        auth.userDetailsService( userDetailsService );
        //auth.inMemoryAuthentication().withUser( "muthu" ).password( "muthu" ).roles( "ADMIN" ).and().withUser( "saran" ).password( "saran" ).roles( "INSTALLER" );
    }

    //Authorization process
    @Override
    protected void configure( HttpSecurity http ) throws Exception
    {
        //  /** matches all path
        /*http.authorizeRequests()
                .antMatchers( "/admin" ,"/addUser").hasAuthority( Permissions.ADMIN.name() )
                .antMatchers( "/user" ).hasAnyAuthority( Permissions.INSTALLER.name() , Permissions.ADMIN.name() )
                .antMatchers( "/**" ).permitAll()
                .and().formLogin();*/

        http.csrf().disable().authorizeRequests()
                .antMatchers( "/login","/addUser" ).permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint( jwtAuthenticationEntryPoint ).and()
                .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );

        http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return NoOpPasswordEncoder.getInstance();
    }
}
