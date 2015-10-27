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

    @RequestMapping(value = '/questions/{username}', method = RequestMethod.GET)
    def findAll(@PathVariable username) {
        def questions = findAllQuestions()
        questions = filterQuestions(questions, username)
        return questions.collect { questionMap(it) }
    }

    @RequestMapping(value = '/questions/{username}/{questionId}', method = RequestMethod.GET)
    def question(@PathVariable String username, @PathVariable String questionId) {
        def question = findQuestionById(questionId)
        question = filterQuestions([question], username)[0]
        return questionMap(question)
    }

    @RequestMapping(value = '/questions/{username}/{questionId}/answer', method = RequestMethod.POST)
    def answerQuestion(@PathVariable String username, @PathVariable String questionId, @RequestBody body) {
        if (username && questionId && body.answer) {
            Answer answer = new Answer(username: username, answer: body.answer)
            quizClient.answerQuestion(questionId, answer)
            new ResponseEntity(HttpStatus.CREATED)
        } else {
            new ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    def filterQuestions(List<Question> questions, String username) {
        questions.each { Question q ->
            q.answers.removeAll { it?.username != username } //remove answers the user did not submit
        }
        questions
    }

    def questionMap(Question question) {
        [id: question.id, question: question.question, answer: question.answers[0]?.answer ?: '']
    }

    /*
     * Hystrix commands/fallbacks
     */

    @HystrixCommand(fallbackMethod = "defaultQuestions")
    def findAllQuestions() {
        quizClient.findAll()
    }

    def defaultQuestions() {
        []
    }

    @HystrixCommand(fallbackMethod = "defaultQuestion")
    def findQuestionById(String questionId) {
        quizClient.findById(questionId)
    }

    def defaultQuestion(String questionId) {
        null
    }
}
