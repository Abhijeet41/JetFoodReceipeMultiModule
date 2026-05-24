package com.abhi46.dsa.TestProgramm.arrays

fun main(){
    val myArray = charArrayOf('K', 'o', 't', 'l', 'i', 'n')

   // reverseArray(myArray)
    reverseArrayOfChar(myArray)

}

fun reverseArrayOfChar(myArray: CharArray) {
    var startIndex = 0
    var lastIndex = myArray.size - 1

    while (startIndex < lastIndex){
        val temp = myArray[startIndex]
        myArray[startIndex] = myArray[lastIndex]
        myArray[lastIndex] = temp
        startIndex ++
        lastIndex --
    }
    println(myArray.joinToString())

}

fun reverseArray(myArray: CharArray) {
    // space complexity is 1 because we used tempArray
    var tempArray = CharArray(myArray.size)
    var lastIndex = myArray.size - 1

    for (i in 0..lastIndex){
        tempArray[i] = myArray[lastIndex]
        lastIndex --
    }
    println(tempArray.joinToString())
}