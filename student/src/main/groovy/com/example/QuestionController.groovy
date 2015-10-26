package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.discovery.DiscoveryClient
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
    QuizClient quizClient

    @RequestMapping(value = '/questions', method = RequestMethod.GET)
    def questions() {
        quizClient.getQuestions()
    }

    @RequestMapping(value = '/questions/{questionId}', method = RequestMethod.GET)
    def question(@PathVariable String questionId) {
        quizClient.getQuestion(questionId)
    }

    @RequestMapping(value = '/questions/{questionId}/answer', method = RequestMethod.POST)
    def answerQuestion(@PathVariable String questionId, @RequestBody body) {
        if (questionId && body.answer) {
            Answer answer = new Answer(username: 'my pal', answer: body.answer)
            quizClient.answerQuestion(questionId, answer)
            new ResponseEntity(HttpStatus.CREATED)
        } else {
            new ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }
}