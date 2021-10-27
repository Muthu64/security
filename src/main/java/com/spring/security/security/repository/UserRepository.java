package com.spring.security.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spring.security.security.domain.UserDomain;

@Repository
public interface UserRepository extends MongoRepository<UserDomain, String>
{
    UserDomain findByName( String userName );
}
