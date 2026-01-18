package com.abhi41.jetfoodrecipeappmultimodule.TestProgramm.numbers_problem

fun main(){
    var total = 10
    var firstNum = 0
    var secondNum = 1
    var sum = 0
    for (i in 1..total){
        println(firstNum)
        sum = firstNum + secondNum  //0 || 0 + 1 = 1 || 1+2 = 3|| 3+2 =5|| 5+3 = 8
        firstNum = secondNum
        secondNum = sum
    }

}