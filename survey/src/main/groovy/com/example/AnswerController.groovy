package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AnswerController {

    @Autowired
    QuestionService questionService

    @RequestMapping(value = '/answers/{username}', method = RequestMethod.GET)
    def findAllAnswersByUsername(@PathVariable username) {
        return questionService.findAllAnswersByUsername(username)
    }

    @RequestMapping(value = '/answers/{username}', method = RequestMethod.DELETE)
    def deleteAllAnswersByUsername(@PathVariable username) {
        questionService.deleteAllAnswersByUsername(username)
        return new ResponseEntity(HttpStatus.OK)
    }
}
