package com.abhi41.dsa.TestProgramm.numbers_problem

fun main(){
    var number = 2432432
    var count = 0

    while (number != 0){
        count ++
        number = number / 10
    }
    print(count)
}