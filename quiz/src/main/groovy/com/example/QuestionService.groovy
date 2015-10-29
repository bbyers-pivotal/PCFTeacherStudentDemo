package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class QuestionService {

    @Autowired
    QuestionRepository questionRepository

    @Autowired
    AnswerRepository answerRepository

    @Autowired
    UserClient userClient

    @Autowired
    MongoOperations mongoOperations

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
        def userAnswers = answerRepository.findAllByUsername(username) //find all answers for the user
        Query questionQuery = Query.query(Criteria.where("answers.id").in(userAnswers.id)) //create query to find child answers
        return mongoOperations.find(questionQuery, Question) //find all the questions with answers by the u
    }

    def deleteAllAnswersByUsername(String username) {
        def userAnswers = answerRepository.findAllByUsername(username) //find all answers for the user
        Query questionQuery = Query.query(Criteria.where("answers.id").in(userAnswers.id)) //create query to find child answers
        List<Question> questions = mongoOperations.find(questionQuery, Question) //find all the questions with answers by the user

        //remove all the answers from the question for the user
        questions.each { Question q ->
            q.answers.removeAll { it.id in userAnswers.id }
            questionRepository.save(q)
        }

        //finally, remove the answers themselves
        Query removeQuery = Query.query(Criteria.where("id").in(userAnswers.id))
        return mongoOperations.remove(removeQuery, Answer)
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
            new ResponseEntity(HttpStatus.CREATED)
        } else {
            new ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    def deleteAll() {
        questionRepository.deleteAll()
        answerRepository.deleteAll()
        new ResponseEntity(HttpStatus.OK)
    }

    def delete(String id) {
        //remove all answers for the question
        List<Answer> answers = questionRepository.findById(id).answers
        Query removeQuery = Query.query(Criteria.where("id").in(answers.id))
        mongoOperations.remove(removeQuery, Answer)

        //finally, remove the question itself
        questionRepository.delete(id)
        new ResponseEntity(HttpStatus.OK)
    }
}
