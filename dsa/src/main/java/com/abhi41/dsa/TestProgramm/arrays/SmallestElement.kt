package com.abhi41.dsa.TestProgramm.arrays

fun main(){
    val arr = intArrayOf(3, 5, 2, 9, 1)
    var min = arr[0]

    for ( i in arr){
        if (i < min){
            min = i
        }
    }
    println("Smallest number is $min")
}