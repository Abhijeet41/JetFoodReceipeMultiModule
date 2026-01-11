package com.abhi41.jetfoodrecipeappmultimodule.TestProgramm.remove_duplicates

fun main(){
    val word = "banana"
    var result = ""

    for (i in word.indices){
        var isDuplicate = false

        for (j in 0 until i){
            if (word[j] == word[i]){
                isDuplicate = true
                break
            }
        }

        if (!isDuplicate){
            result = result + word[i]
        }
    }
    println("After removing duplicates: $result")

}