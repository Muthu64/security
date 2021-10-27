package com.spring.security.security.userDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.security.security.domain.UserDomain;
import com.spring.security.security.model.MyUserDetails;
import com.spring.security.security.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername( String userName ) throws UsernameNotFoundException
    {
        UserDomain userDomain = this.userRepository.findByName( userName );
        this.validateUserDomain( userDomain, userName );
        MyUserDetails myUserDetails = new MyUserDetails( userDomain );
        System.out.println("my userDetails-----------"+myUserDetails);
        return myUserDetails;
    }

    private void validateUserDomain( UserDomain userDomain, String userName )
    {
        if( userDomain == null || userDomain.getRoles() == null || userDomain.getRoles().size() == 0 )
        {
            throw new UsernameNotFoundException( userName + " not found." );
        }
    }
}
