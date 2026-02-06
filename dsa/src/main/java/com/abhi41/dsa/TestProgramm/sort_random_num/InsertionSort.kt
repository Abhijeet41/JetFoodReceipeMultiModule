package com.abhi41.dsa.TestProgramm.sort_random_num

fun main() {
    val myArray:IntArray = intArrayOf(8, 5, 6, 7, 2)
    //sort array using insertion sort where we use shifting mechanism
    for (i in 0+1..myArray.size -1){//8,5,6,7,2  	||5,8,6,7,2
        var key = myArray[i]       //key = 5 index=1
        var j = i-1                  //j = 8 index = 0
        while (j >= 0 && myArray[j] > key){     // 8>5 then shift  8 at 1 index
            myArray[j+1] = myArray[j]      //shifting 8 from 0 to 1 index
            j = j-1                         // -1
        }
        myArray[j+1] = key    //myArray[-1+1] myArray[0] = 5
    }
    println(myArray.joinToString())
}