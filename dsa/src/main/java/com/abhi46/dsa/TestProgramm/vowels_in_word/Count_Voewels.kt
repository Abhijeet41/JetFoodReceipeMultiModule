package com.abhi46.dsa.TestProgramm.vowels_in_word

fun main(){
    val vowels = "aeiouAEIOU"
    val word = "abhijeet"
    var count = 0
    for (c in word){
        if (c in vowels){
            count ++
        }
    }
    println("The word in vowe ls is $count")
}