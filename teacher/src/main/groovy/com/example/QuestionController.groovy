package com.example

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class QuestionController {

    @RequestMapping('/questions')
    def questions() {
        [
            [
                question: 'How are you today?',
                responses: [
                    [
                        name: 'Brian',
                        response: 'pretty good'
                    ],[
                        name: 'Staci',
                        response: 'meh'
                    ]
                ]
            ]
        ]
    }
}
