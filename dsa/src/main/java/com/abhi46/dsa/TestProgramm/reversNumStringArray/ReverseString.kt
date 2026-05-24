package com.abhi46.dsa.TestProgramm.reversNumStringArray

fun main() {
    //reverse("Kotlin programmer")
    // reverseNumber(123456)
  /*  val myArray = charArrayOf('K', 'o', 't', 'l', 'i', 'n')
    reverseArrayByCreatingNewOne(myArray)*/
    reverseNumberWithOutConvertedIntoString(123456)

}


fun reverse(str: String)  {
    println("Input: $str")

    var result = ""
    var lastIndex = str.length - 1

    for (i in 0..lastIndex) {
        result = result + str[lastIndex]
        lastIndex--
    }
    println(result)

}

fun reverseNumber(num: Int) {
    var result = ""
    var strNum = num.toString()

    var lastIndex = strNum.length -1

    for (i in 0..lastIndex){
        result = result + strNum[lastIndex]
        lastIndex--
    }
    println("reversed num is : ${result.toInt()}")

}

fun reverseArrayByCreatingNewOne(char: CharArray) {
    println("Input array: ${char.joinToString()}")
    //reversedArray is empty array that refer later
    val reversedArray = CharArray(char.size)
    var lastIndex = char.size - 1
    var firstIndex = 0
    for (i in 0 .. lastIndex) {
        reversedArray[i] = char[lastIndex]
        lastIndex --
    }
    println("Reversed array: ${reversedArray.joinToString()}")


}

fun reverseNumberWithOutConvertedIntoString(num: Int) {
    var number = num
    var lastDigit = 0
    var reversedNum = 0
    while (number > 0){
        lastDigit = number % 10
        reversedNum = reversedNum * 10 + lastDigit
        number = number / 10
    }
    println("you reversed number is: $reversedNum")
}
