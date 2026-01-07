package com.abhi41.jetfoodrecipeappmultimodule.TestProgramm.sort_random_num.bubble_sort

fun main(){
    val myArray = intArrayOf(8, 5, 6, 7, 2)
   // sortRandomNumber(myArray) //using bubble sort
   // sortRandomNumberUsingBubbleSort(myArray)
    sortRandomNumber2(myArray)
}

fun sortRandomNumber(myArray: IntArray) {
   /* for (i in myArray.indices){
        for (j in i +1 .. myArray.size-1){  //8, 5, 6, 7, 2  //5,8,6,7,2 //5,6,8,7,2 //5,6,7,8,2 //5,6,7,2,8
            if (myArray[i] > myArray[j]){
                var temp = myArray[i]
                myArray[i] = myArray[j]
                myArray[j] = temp
            }
        }
    }

    for (i in myArray){
        print(i)
    }*/

    for (i in myArray.indices){
        for (j in i+1..myArray.size - 1){ //8,5,6,7,2 // 5,8,6,7,2 //  5,6,8,7,2 //5,6,7,8,2//5,6,7,2,8
            if (myArray[i] > myArray[j]){      //5,6,7,2 // 5,6,2,7
                var temp = myArray[i] //8         //5,6,2  //5,2,6
                myArray[i] = myArray[j]//5       //5,2  //2,5
                myArray[j] = temp //8              //2
            }
            //85672
            //5672
            //672
            //72
            //2
            print(myArray[j])
        }
      //  println()
    }
    for (num in myArray){
        print(num)
    }
}

fun sortRandomNumberUsingBubbleSort(myArray: IntArray) {
    val lastIndex = myArray.size -1
    for (i in myArray.indices){                   //8,5,6,7,2 //5,8,6,7,2 //5,6,8,7,2 //5,6,7,8,2 //5,6,7,2,8
        for (j in 0..lastIndex-i-1){        //5,6,7,2 //5,6,2,7
            if (myArray[j] > myArray[j+1]){      //5,6,2 //5,2,6
                val temp = myArray[j]  //8       //5,2 // 2,5
                myArray[j] = myArray[j+1]  //5   //2
                myArray[j+1] = temp //8
            }
            //85672
            //8567
            //856
            //85
            //8
           // print(myArray[j])
        }
        //println()
    }

    for ( num in myArray){
        print(num)
    }
}

fun sortRandomNumber2(myArray: IntArray) {//8,5,6,7,2
    for ( i in myArray.indices){
        for (j in i+1 .. myArray.size-1){
            if (myArray[j] < myArray[i]){  //is 5<8= Y
                val temp = myArray[i]
                myArray[i] = myArray[j]
                myArray[j] = temp
            }
        }
    }
    for ( num in myArray){
        print(num)
    }
}