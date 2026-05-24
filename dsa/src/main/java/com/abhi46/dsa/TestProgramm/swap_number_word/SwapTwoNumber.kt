package com.abhi46.dsa.TestProgramm.swap_number_word

fun main(){
    var a=10
    var b=20
    swapNumUsingThirdVariable(a,b)

    swapWithOutUsingThirdVariable(a,b)
}

fun swapNumUsingThirdVariable(a: Int, b: Int) {
    var num1 = a
    var num2 = b
    println("Befor Swaping 2 num : $num1 and $num2")

    var temp = a
    num1 = b
    num2 = temp

    println("After Swaping 2 num : $num1 and $num2")
}

fun swapWithOutUsingThirdVariable(a: Int, b: Int){
    var num1 = a  //10
    var num2 = b  //20
    println("Befor Swaping 2 num : $num1 and $num2")

    num1 = num1 + num2  // 10 + 20 = 30
    num2 = num1 - num2  // 30 -20 = 10
    num1 = num1 - num2 // 30 - 10 = 20
    println("After Swaping 2 num : $num1 and $num2")

}