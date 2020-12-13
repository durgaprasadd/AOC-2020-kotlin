package com.day9

import com.day1.calculateAsPairSumOptimize
import com.helpers.readDataAsLongList

fun findInvalidNumber(preamble: Int, input: List<Long>): Long {
    for (i in preamble until input.size){
        val result = calculateAsPairSumOptimize(input.slice(i-preamble until i), input[i])
        if (result == null){
            return input[i]
        }
    }
    return 0
}
fun main() {
    val input = readDataAsLongList("day9", "data.txt")
    val invalidNumber = findInvalidNumber(25,input)
    println(invalidNumber)
    val series = findSeriesSum(input, invalidNumber)
    println(series.min()!! + series.max()!!)

}

fun findSeriesSum(input: List<Long>, sum: Long) : List<Long>{
    for (i in input.indices){
        var actualSum = 0L
        val series = mutableListOf<Long>()
        for (j in i until input.size){
            actualSum += input[j]
            series.add(input[j])
            if (actualSum > sum){
                break
            }
            if (actualSum == sum){
                return series
            }
        }
    }
    return emptyList()
}