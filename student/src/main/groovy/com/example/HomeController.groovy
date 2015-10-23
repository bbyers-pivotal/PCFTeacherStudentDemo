package com.example

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Created by bbyers on 10/23/15.
 */

@Controller
class HomeController {

    @RequestMapping('/')
    def home() {
        return 'home'
    }
}
