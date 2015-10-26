package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class QuestionService {

    @Autowired
    QuestionRepository questionRepository

    @Autowired
    AnswerRepository answerRepository

    def findAll() {
        return questionRepository.findAll()
    }

    def findById(String id) {
        return questionRepository.findById(id)[0]
    }

    def save(String question) {
        Question q = new Question(question: question)
        return questionRepository.save(q)
    }

    Boolean recordAnswer(String id, String username, String answer) {
        Question question = questionRepository.findById(id)[0]
        if (question) {
            Answer a = new Answer(answer: answer, username: username)
            answerRepository.save(a)
            question.answers << a
            questionRepository.save(question)
            return true
        } else {
            return false
        }
    }
}
