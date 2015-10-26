package com.example

import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Created by bbyers on 10/26/15.
 */
public interface AnswerRepository extends MongoRepository<Answer, String> {
    List<Answer> findById(String id)
}