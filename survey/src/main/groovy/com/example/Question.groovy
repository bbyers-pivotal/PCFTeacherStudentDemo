package com.example

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Question {

    @Id
    String id
    String question

    @DBRef
    List<Answer> answers = []
}
