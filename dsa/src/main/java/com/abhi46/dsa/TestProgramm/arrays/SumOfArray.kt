package com.abhi46.dsa.TestProgramm.arrays

fun main(){
    val arr = intArrayOf(2, 4, 6, 8)
    var sum = 0

    for (i in arr){
        sum = sum +i
    }
    println("Sum of array: $sum")

}