package com.abhi41.dsa.TestProgramm.sort_random_num.selection_sort

import kotlin.math.min

fun main() {
    val myArray = intArrayOf(8, 5, 6, 7, 2)
   // SortRandomNumUsingSelectionSort(myArray)
    SortRandomNumUsingSelectionSort2(myArray)
}

fun SortRandomNumUsingSelectionSort(myArray: IntArray) {

    for (i in myArray.indices) {

        var minIndex = i

        for (j in i + 1 until  myArray.size) { //8,5,6,7,2 ||2,5,6,7,8
            if (myArray[j] < myArray[minIndex]) {  //is 5<8 =y || is 6<5=n || is 7<5=n || is 2<5=y
                minIndex = j                    // minIndex = 1|| skip     ||skip      || minIndex = 4
            }
        }
        //Swap only if needed
        if (minIndex != i) {                //4!=1  ||
            val temp = myArray[i]           //8
            myArray[i] = myArray[minIndex]  //2
            myArray[minIndex] = temp        //8
        }
    }
    for (num in myArray){
        print(num)
    }
}

fun SortRandomNumUsingSelectionSort2(myArray: IntArray){
    //85672

    for (i in myArray.indices){

        var minIndex = i   //0

        for (j in i+1 .. myArray.size - 1){   //8,5,6,7,2

            if (myArray[j] < myArray[minIndex]){   //is 5<8 =y  ||is 6<5=n  ||is 7 <5=n ||is 2<5=y
                minIndex = j                       //1          //skip     // skip      ||4
            }
        }

        if (minIndex != i){  //4!=0
            val temp = myArray[i]    //8
            myArray[i] = myArray[minIndex] // 2
            myArray[minIndex] = temp //8
        }

    }
    for (num in myArray){
        print(num)
    }

}