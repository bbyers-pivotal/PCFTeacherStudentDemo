package com.example

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AnswerController {

    @Autowired
    QuizClient quizClient

    @RequestMapping(value = '/answers/{username}', method = RequestMethod.GET)
    def answersByUsername(@PathVariable String username) {
        return findAllAnswersByUsername(username)
    }

    @RequestMapping(value = '/answers/{username}', method = RequestMethod.DELETE)
    def deleteAnswersByUsername(@PathVariable String username) {
        return quizClient.deleteAllAnswersByUsername(username)
    }

    /*
     * Hystrix commands/fallbacks
     */

    @HystrixCommand(fallbackMethod = "defaultAnswers")
    def findAllAnswersByUsername(String username) {
        return quizClient.findAllAnswersByUsername(username)
    }

    def defaultAnswers(String username) {
        return []
    }
}
