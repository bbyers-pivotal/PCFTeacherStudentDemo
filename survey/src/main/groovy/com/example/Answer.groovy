package com.example

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document
class Answer {

    @Id
    String id
    String username
    String answer
}
