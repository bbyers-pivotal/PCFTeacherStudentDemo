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

    @Autowired
    LoadService loadService


    @RequestMapping(value = '/questions/{username}', method = RequestMethod.GET)
    def findAll(@PathVariable username) {
        def questions = findAllQuestions()
        questions = filterQuestions(questions, username)
        return questions.collect { questionMap(it) }
    }

    @RequestMapping(value = '/questions/{username}/{id}', method = RequestMethod.GET)
    def question(@PathVariable String username, @PathVariable String id) {
        def question = findQuestionById(id)
        question = filterQuestions([question], username)[0]
        return questionMap(question)
    }

    @RequestMapping(value = '/questions/{username}/{id}/answer', method = RequestMethod.POST)
    def answerQuestion(@PathVariable String username, @PathVariable String id, @RequestBody body) {
        if (username && id && body.answer) {
            Answer answer = new Answer(username: username, answer: body.answer)
            surveyClient.answerQuestion(id, answer)
            return new ResponseEntity(HttpStatus.CREATED)
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    def filterQuestions(List<Question> questions, String username) {
        questions.each { Question q ->
            q.answers.removeAll { it?.username != username } //remove answers the user did not submit
        }
        return questions
    }

    def questionMap(Question question) {
        return [id: question.id, question: question.question, answer: question.answers[0]?.answer ?: '']
    }

    @RequestMapping('/makeItAngry')
    def makeItAngry() {
        loadService.bogItDown()
        return true
    }

    /*
     * Hystrix commands/fallbacks
     */

    @HystrixCommand(fallbackMethod = "defaultQuestions")
    def findAllQuestions() {
        return surveyClient.findAll()
    }

    def defaultQuestions() {
        return []
    }

    @HystrixCommand(fallbackMethod = "defaultQuestion")
    def findQuestionById(String id) {
        return surveyClient.findById(id)
    }

    def defaultQuestion(String id) {
        return null
    }
}
