package com.example

import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient("quiz")
public interface QuizClient {

    @RequestMapping(method = RequestMethod.GET, value = "/questions")
    List<Question> findAll()

    @RequestMapping(method = RequestMethod.GET, value = "/questions/{id}")
    Question findById(@PathVariable("id") String id)

    @RequestMapping(method = RequestMethod.POST, value = "/questions", consumes = "application/json")
    askQuestion(Question question)

    @RequestMapping(method = RequestMethod.DELETE, value = "/questions")
    deleteAll()

    @RequestMapping(method = RequestMethod.DELETE, value = "/questions/{id}")
    delete(@PathVariable("id") String questionId)
}
