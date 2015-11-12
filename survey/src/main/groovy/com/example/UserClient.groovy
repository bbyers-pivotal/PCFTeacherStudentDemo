package com.example

import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient("user")
public interface UserClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    List<User> findAll()

    @RequestMapping(method = RequestMethod.GET, value = "/users/{username}")
    User findByUsername(@PathVariable("username") String username)

    @RequestMapping(method = RequestMethod.POST, value = "/users", consumes = "application/json")
    saveUser(User user)
}
