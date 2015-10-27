package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    UserRepository userRepository

    def findAll() {
        return userRepository.findAll()
    }

    def findByUsername(String username) {
        return userRepository.findByUsername(username)[0]
    }

    def save(User user) {
        return userRepository.save(user)
    }
}
