package com.example

import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient("quiz")
public interface QuizClient {

    @RequestMapping(method = RequestMethod.GET, value = "/questions")
    List<Question> getQuestions()

    @RequestMapping(method = RequestMethod.GET, value = "/questions/{questionId}")
    Question getQuestion(@PathVariable("questionId") String questionId)

    @RequestMapping(method = RequestMethod.POST, value = "/questions", consumes = "application/json")
    askQuestion(Question question)
}