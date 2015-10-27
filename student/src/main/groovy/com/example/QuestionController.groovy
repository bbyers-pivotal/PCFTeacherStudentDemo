package com.example

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
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
    QuizClient quizClient

    @RequestMapping(value = '/questions', method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "defaultQuestions")
    def questions() {
        quizClient.findAll()
    }

    @RequestMapping(value = '/questions/{questionId}', method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "defaultQuestion")
    def question(@PathVariable String questionId) {
        quizClient.findById(questionId)
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

    /*
     * Hystrix fallbacks
     */

    def defaultQuestions() {
        []
    }

    def defaultQuestion(String questionId) {
        null
    }
}
