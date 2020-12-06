package com.day1

import com.helpers.readDataAsIntList

fun calculateAsTripletSum(expenses: List<Int>, sum: Int): Int {
    var i = 0
    while (i < expenses.size - 2) {
        var j = i + 1
        while (j < expenses.size - 1) {
            var k = j + 1
            while (k < expenses.size) {
                if (expenses[i] + expenses[j] + expenses[k] == sum) {
                    return expenses[i] * expenses[j] * expenses[k]
                }
                k += 1;
            }
            j += 1;
        }
        i += 1
    }
    return 0
}

fun calculateAsPairSum(expenses: List<Int>, sum: Int): Int {
    for (expense in expenses) {
        val remExpense = sum - expense
        if (expenses.contains(remExpense)) {
            return expense * remExpense
        }
    }
    return 0
}

fun getMutableMap(input: List<Int>): MutableMap<Int,Int>{
    val mutableMap = mutableMapOf<Int,Int>()
    for (ele in input){
        mutableMap[ele] = ele
    }
    return mutableMap
}

fun calculateAsPairSumOptimize(expenses: List<Int>, sum: Int): Int? {
    val expensesMap = getMutableMap(expenses)
    return calculateAsPairSumOptimize(expenses,sum,expensesMap)
}

fun calculateAsPairSumOptimize(expenses: List<Int>, sum: Int, expensesMap: MutableMap<Int,Int>): Int? {
    for (expense in expenses) {
        val remExpense = sum - expense
        val result = expensesMap[remExpense]
        if (result != null) {
            return remExpense * expense
        }
    }
    return null
}

fun calculateAsTripletSumOptimize(expenses: List<Int>, sum: Int): Int? {
    val expensesMap = getMutableMap(expenses)
    for (expense in expenses){
        val remExpense = sum - expense
        val result = calculateAsPairSumOptimize(expenses, remExpense, expensesMap)
        if (result != null){
            return expense * result
        }
    }
    return null
}

fun main() {
    val input = readDataAsIntList("day1", "data.txt")
    val sum = 2020
    println(calculateAsPairSum(input,sum))
    println(calculateAsPairSumOptimize(input,sum))

    println(calculateAsTripletSum(input,sum))
    println(calculateAsTripletSumOptimize(input, sum))
}
