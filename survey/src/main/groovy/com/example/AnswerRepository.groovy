package com.example

import org.springframework.data.mongodb.repository.MongoRepository

public interface AnswerRepository extends MongoRepository<Answer, String> {
    List<Answer> findAllByUsername(String username)
}