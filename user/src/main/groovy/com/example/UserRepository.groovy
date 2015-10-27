package com.example

import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Created by bbyers on 10/26/15.
 */
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByUsername(String username)
}