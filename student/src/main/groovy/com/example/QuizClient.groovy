package com.example

import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient("quiz")
public interface QuizClient {

    @RequestMapping(method = RequestMethod.GET, value = "/questions")
    List<Question> findAll()

    @RequestMapping(method = RequestMethod.GET, value = "/questions/{questionId}")
    Question findById(@PathVariable("questionId") String questionId)

    @RequestMapping(method = RequestMethod.POST, value = "/questions/{questionId}/answer", consumes = "application/json")
    answerQuestion(@PathVariable("questionId") String questionId, Answer answer)
}
