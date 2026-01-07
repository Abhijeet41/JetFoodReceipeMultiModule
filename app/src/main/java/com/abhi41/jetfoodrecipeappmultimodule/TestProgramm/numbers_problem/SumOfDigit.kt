package com.abhi41.jetfoodrecipeappmultimodule.TestProgramm.numbers_problem

fun main(){
    var num  = 123
    var sum = 0

    while (num != 0){
        sum = sum + num % 10 // 3  || 2 || 1
        num  = num /10       //12 || 1 || 0
    }
    print(sum)
}