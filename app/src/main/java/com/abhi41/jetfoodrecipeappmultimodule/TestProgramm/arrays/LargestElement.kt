package com.abhi41.jetfoodrecipeappmultimodule.TestProgramm.arrays

fun main(){

    val arr = intArrayOf(3, 5, 2, 9, 1)
    var max = arr[0]

    for (i in arr){
        if (i > max){
            max = i
        }
    }
    println("Largest element: $max")


}