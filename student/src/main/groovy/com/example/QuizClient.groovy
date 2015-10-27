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

    @RequestMapping(method = RequestMethod.POST, value = "/questions/{id}/answer", consumes = "application/json")
    answerQuestion(@PathVariable("id") String id, Answer answer)

    @RequestMapping(method = RequestMethod.GET, value = "/answers/{username}")
    findAllAnswersByUsername(@PathVariable("username") String username)

    @RequestMapping(method = RequestMethod.DELETE, value = "/answers/{username}")
    deleteAllAnswersByUsername(@PathVariable("username") String username)
}
