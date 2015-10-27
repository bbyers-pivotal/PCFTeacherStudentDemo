package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
class UserController {

    @Autowired
    UserService userService

    @RequestMapping(value = '/users', method = RequestMethod.GET)
    def findAll() {
        return userService.findAll().collect { User u -> [id: u.id, username: u.username ] }
    }

    @RequestMapping(value = '/users/{username}', method = RequestMethod.GET)
    def findByUsername(@PathVariable username) {
        return userService.findByUsername(username.trim())
    }

    @RequestMapping(value = '/users', method = RequestMethod.POST)
    def saveUser(@RequestBody body) {
        if (body.username.trim()) {
            userService.save(new User(username: body.username.trim()))
            new ResponseEntity(HttpStatus.CREATED)
        } else {
            new ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }
}
