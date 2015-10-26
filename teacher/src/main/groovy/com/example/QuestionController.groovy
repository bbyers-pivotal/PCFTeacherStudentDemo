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
    QuizClient quizClient

    @RequestMapping(value = '/questions', method = RequestMethod.GET)
    def questions() {
        quizClient.getQuestions()
    }

    @RequestMapping(value = '/questions/{questionId}', method = RequestMethod.GET)
    def question(@PathVariable String questionId) {
        quizClient.getQuestion(questionId)
    }

    @RequestMapping(value = '/questions', method = RequestMethod.POST)
    def askQuestion(@RequestBody body) {
        if (body.question) {
            Question question = new Question(question: body.question)
            quizClient.askQuestion(question)
            new ResponseEntity(HttpStatus.CREATED)
        } else {
            new ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }
}
