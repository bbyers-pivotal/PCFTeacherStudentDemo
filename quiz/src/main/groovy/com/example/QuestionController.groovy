package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
class QuestionController {

    @Autowired
    QuestionService questionService

    @RequestMapping(value = '/questions', method = RequestMethod.GET)
    def questions() {
        return questionService.findAll()
    }

    @RequestMapping(value = '/questions/{id}', method = RequestMethod.GET)
    def question(@PathVariable id) {
        return questionService.findById(id)
    }

    @RequestMapping(value = '/questions', method = RequestMethod.POST)
    def saveQuestion(@RequestBody body) {
        if (body.question) {
            questionService.save(body.question)
            return new ResponseEntity(HttpStatus.CREATED)
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @RequestMapping(value = '/questions/{id}/answer', method = RequestMethod.POST)
    def recordAnswer(@PathVariable id, @RequestBody body) {
        if (id && body.username && body.answer) {
            questionService.recordAnswer(id, body.username, body.answer)
            return new ResponseEntity(HttpStatus.CREATED)
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST)
        }

    }

}
