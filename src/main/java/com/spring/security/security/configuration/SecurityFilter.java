package com.spring.security.security.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.security.security.jwt.JwtUtils;
import com.spring.security.security.userDetail.MyUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class SecurityFilter extends OncePerRequestFilter
{
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain ) throws ServletException, IOException
    {
        final String requestTokenHeader = httpServletRequest.getHeader( "token" );

        String username = null;
        String jwtToken = null;

        if( requestTokenHeader != null && requestTokenHeader.startsWith( "Bearer " ) )
        {
            jwtToken = requestTokenHeader.substring( 7 );

            try
            {
                username = JwtUtils.extractUserName( jwtToken );
            }
            catch( IllegalArgumentException e )
            {
                System.out.println( "Unable to get JWT Token" );
            }
            catch( ExpiredJwtException e )
            {
                System.out.println( "JWT Token has expired" );
            }
        }
        else
        {
            logger.warn( "JWT Token does not begin with Bearer String" );
        }

        if( username != null && SecurityContextHolder.getContext().getAuthentication() == null )
        {
            UserDetails userDetails = this.myUserDetailsService.loadUserByUsername( username );

            if( JwtUtils.validateToken( jwtToken, userDetails ) )
            {
                //UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = JwtUtils.getAuthenticationToken( jwtToken, SecurityContextHolder.getContext().getAuthentication(), userDetails );
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities() );
                //UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities() );
                usernamePasswordAuthenticationToken.setDetails( new WebAuthenticationDetailsSource().buildDetails( httpServletRequest ) );
                SecurityContextHolder.getContext().setAuthentication( usernamePasswordAuthenticationToken );
            }
        }
        filterChain.doFilter( httpServletRequest, httpServletResponse );
    }
}
