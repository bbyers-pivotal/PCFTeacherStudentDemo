package com.example

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {

    @RequestMapping('/')
    def home() {
        return 'home'
    }
}