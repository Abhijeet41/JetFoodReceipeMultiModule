package com.abhi41.jetfoodrecipeappmultimodule.TestProgramm.swap_number_word


fun main(){
    var word1 = "Java"
    var word2 = "Kotlin"

    println("Before swapping:")
    println("word1 = $word1, word2 = $word2")

    word1 = word1 + word2
    word2 = word1.substring(0,word1.length - word2.length)// JavaKotlin-Kotlin  10 - 6 = 4  || word2 = Java
    word1 = word1.substring(word2.length) //JavaKotlin - Java 10-4 = Kotlin
    println("After swapping:")
    println("word1 = $word1, word2 = $word2")
}