package com.abhi41.dsa.TestProgramm.numbers_problem

fun main(){
    val n = 5
    var factorial = 1

    for (i in 1..n){
        factorial = factorial * i
    }
    print(factorial)
}