package com.example

import org.springframework.data.mongodb.repository.MongoRepository

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findById(String id)
}