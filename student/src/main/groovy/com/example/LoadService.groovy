package com.example

import org.springframework.stereotype.Service

@Service
class LoadService {

    public void bogItDown() {
        int number = 35
        for(int i = 1; i <= number; i++){
            fibonacciRecusion(i)
        }
    }

    private int fibonacciRecusion(int number){
        if (number == 1 || number == 2) {
            return 1
        }

        return fibonacciRecusion(number - 1) + fibonacciRecusion(number - 2)
    }
}
