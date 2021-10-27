package com.spring.security.security.jwt;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils
{
    private static final String SECRET = "qwerty";
    private static final String AUTHORITIES_KEY = "roles";

    public static String extractUserName( String token )
    {
        return extractClaim( token, Claims::getSubject );
    }

    private static <T> T extractClaim( String token, Function<Claims, T> claimsResolver )
    {
        final Claims claims = extractAllClaims( token );
        return claimsResolver.apply( claims );
    }

    private static Claims extractAllClaims( String token )
    {
        return Jwts.parser().setSigningKey( SECRET ).parseClaimsJws( token ).getBody();
    }

    public static String generateToken( String subject )
    {
        Map<String, Object> claims = new HashMap<>();
        return createToken( claims, subject );
    }

    private static boolean isTokenExpired( String token )
    {
        return extractExpiration( token ).before( new Date() );
    }

    private static Date extractExpiration( String token )
    {
        return extractClaim( token, Claims::getExpiration );
    }

    private static String createToken( Map<String, Object> claims, String username )
    {
        return  Jwts.builder()
                .setClaims( claims )
                .setSubject( username )
                .setIssuedAt( new Date() )
                .setExpiration( new Date( System.currentTimeMillis() + 10000 * 60) )
                .signWith( SignatureAlgorithm.HS256, SECRET ).compact();
    }

    public static boolean validateToken( String token, UserDetails userDetails )
    {
        String extractedUserName = extractUserName( token );
        return ( userDetails.getUsername().equals( extractedUserName ) && !isTokenExpired( token ) );
    }
}
