package com.abhi46.dsa.TestProgramm.swap_number_word


fun main(){
    var strWord = "Hello World"

    var array = strWord.split(" ")

    var result = ""
    var lastIndex = array.size - 1

    for (i in 0..lastIndex){
        result = result + array[lastIndex]
        lastIndex--
    }
    println("After Swap :$result")
}