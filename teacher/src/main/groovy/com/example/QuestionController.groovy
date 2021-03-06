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
    SurveyClient surveyClient

    @RequestMapping(value = '/questions', method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "defaultQuestions")
    def questions() {
        return surveyClient.findAll()
    }

    @RequestMapping(value = '/questions/{id}', method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "defaultQuestion")
    def question(@PathVariable String id) {
        return surveyClient.findById(id)
    }

    @RequestMapping(value = '/questions', method = RequestMethod.POST)
    def askQuestion(@RequestBody body) {
        if (body.question) {
            Question question = new Question(question: body.question)
            surveyClient.askQuestion(question)
            return new ResponseEntity(HttpStatus.CREATED)
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @RequestMapping(value = '/questions', method = RequestMethod.DELETE)
    def deleteAllQuestions() {
        surveyClient.deleteAll()
        return new ResponseEntity(HttpStatus.OK)
    }

    @RequestMapping(value = '/questions/{id}', method = RequestMethod.DELETE)
    def deleteQuestion(@PathVariable String id) {
        surveyClient.delete(id)
        return new ResponseEntity(HttpStatus.OK)
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
