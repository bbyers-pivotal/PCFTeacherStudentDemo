package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class QuestionService {

    @Autowired
    QuestionRepository questionRepository

    @Autowired
    AnswerRepository answerRepository

    @Autowired
    UserClient userClient

    def findAll() {
        return questionRepository.findAll()
    }

    def findById(String id) {
        return questionRepository.findById(id)
    }

    def save(String question) {
        Question q = new Question(question: question)
        return questionRepository.save(q)
    }

    def findAllAnswersByUsername(String username) {
        questionRepository.findAll().findAll { it.answers.username[0] == username }
    }

    def deleteAllAnswersByUsername(String username) {
        def allQuestions = questionRepository.findAll()
        def answeredQuestions = allQuestions.findAll { it.answers.username[0] == username }

        //clear out orphaned
//        questionRepository.findAll().each { Question q ->
//            q.answers.removeAll([null])
//            questionRepository.save(q)
//        }
        answeredQuestions.each { Question q ->
            q.answers.removeAll { it.username[0] == username }
            questionRepository.save(q)
        }
        answerRepository.findAllByUsername(username).each {
            answerRepository.delete(it)
        }
    }

    Boolean recordAnswer(String id, String username, String answer) {
        Question question = questionRepository.findById(id)
        if (username && question) {
            User user = userClient.findByUsername(username) ?: new User(username: username)
            if (!user.id) {
                userClient.saveUser(user)
            }

            Answer a = new Answer(answer: answer, username: username)
            answerRepository.save(a)
            question.answers << a
            questionRepository.save(question)
            return true
        } else {
            return false
        }
    }

    def deleteAll() {
        questionRepository.deleteAll()
        answerRepository.deleteAll()
    }

    def delete(String id) {
        questionRepository.delete(id)
    }
}
