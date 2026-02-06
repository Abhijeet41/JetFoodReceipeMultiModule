package com.abhi41.dsa.TestProgramm.arrays

fun main(){
    val arr = intArrayOf(1, 2, 3, 4)
    reverseNumber(arr)

}

private fun reverseNumber(arr: IntArray) {
    var start = 0
    var end = arr.size - 1

    while (start < end){
        val temp = arr[start]
        arr[start] = arr[end]
        arr[end] = temp
        start ++
        end --
    }
    println(arr.joinToString())
}