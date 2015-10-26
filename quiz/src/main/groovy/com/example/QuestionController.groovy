package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


/**
 * Created by bbyers on 10/26/15.
 */
@RestController
class QuestionController {



    @Autowired
    QuestionService questionService

    @RequestMapping(value = '/questions', method = RequestMethod.GET)
    def questions() {
        return questionService.findAll().collect { Question q -> [id: q.id, question: q.question ] }
    }

    @RequestMapping(value = '/questions/{id}', method = RequestMethod.GET)
    def question(@PathVariable id) {
        return questionService.findById(id)
    }

    @RequestMapping(value = '/questions', method = RequestMethod.POST)
    def saveQuestion(@RequestBody body) {
        if (body.question) {
            questionService.save(body.question)
            new ResponseEntity(HttpStatus.CREATED)
        } else {
            new ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @RequestMapping(value = '/questions/{id}/answer', method = RequestMethod.POST)
    def recordAnswer(@PathVariable id, @RequestBody body) {
        if (id && body.username && body.answer) {
            questionService.recordAnswer(id, body.username, body.answer)
            new ResponseEntity(HttpStatus.CREATED)
        } else {
            new ResponseEntity(HttpStatus.BAD_REQUEST)
        }

    }

}
