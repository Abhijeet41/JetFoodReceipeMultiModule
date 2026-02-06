package com.abhi41.dsa.TestProgramm.vowels_in_word

import kotlin.text.iterator

fun  main(){
    val word = "kotlin"
    printVowelsInChar(word)
}

fun printVowelsInChar(word: String){
    val vowels = "aeiouAEIOU"
    for (ch in word){
        if (ch in vowels){
            print(ch)
        }
    }
}