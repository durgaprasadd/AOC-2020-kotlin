package com.day10

import com.helpers.readDataAsIntList

val recordedCounts  = mutableMapOf<Int,Long>()
fun main() {
    val input = readDataAsIntList("day10", "data.txt")
    val sortedList = input.sorted()
    var prev = 0
    var diff1 = 0
    var diff3 = 1
    for (ele in sortedList) {
        val diff = ele - prev
        prev = ele
        if (diff == 1) {
            diff1 += 1
        }
        if (diff == 3) {
            diff3 += 1
        }
    }
    println(diff1)
    println(diff3)
    println(diff1 * diff3)
    println(getArrangementsCount(sortedList))
}

fun getArrangementsCount(input: List<Int>): Long {
    if (input.size == 1) {
        return 1
    }

    val prev = input.first()
    if (recordedCounts.contains(prev)){
        return recordedCounts[prev]!!
    }

    var index = 1
    var count = 0L
    while (index < input.size) {
        val ele = input[index]
        if (ele - prev <= 3) {
            count += getArrangementsCount(input.slice(index until input.size))
        } else {
            break
        }
        index += 1
    }
    recordedCounts[prev] = count
    return count
}