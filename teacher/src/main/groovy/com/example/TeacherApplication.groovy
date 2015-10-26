package com.example

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.feign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
class TeacherApplication {

    static void main(String[] args) {
        SpringApplication.run TeacherApplication, args
    }
}
