package com.abhi41.jetfoodrecipeappmultimodule.TestProgramm.palindrom_num_word

fun main(){

    //checkWordPalindrone("POP")
    checkNumPalindron(1211)
}

fun checkNumPalindron(num: Int) {
  var tempNum  = num
    var result = 0
    var lastDidit = 0
    while (tempNum != 0){  //1211
        lastDidit = tempNum % 10
        result = result * 10 + lastDidit
        tempNum = tempNum/10
    }
    println(result)

}

fun checkWordPalindrone(checkWord: String) {
    var tempWord = checkWord
    var strReversed = ""
    var lastIndex = tempWord.length -1
    for (i in 0..lastIndex){
        strReversed = strReversed + tempWord[lastIndex]
        lastIndex --
    }

    if (strReversed.equals(checkWord)){
        println("Above word is Palindron")
    }else{
        println("Above word is not Palindron")
    }
}