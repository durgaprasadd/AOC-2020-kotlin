package com.day6

import com.helpers.readData

fun main() {
    val input = readData("day6", "data.txt")
    val groups = input.split("\n\n")
        .map {
            it.split("\n").map {
                it.toSet()
            }
        }
    val allYesAnswers = groups.map {
        it.reduce { acc, chars ->
            acc.plus(chars)
        }
    }.sumBy { it.size }
    val onlyYesAnswers = groups.map {
        it.reduce { acc, chars ->
//            acc.minus(acc.minus(chars))
            acc.intersect(chars)
        }
    }.sumBy { it.size }

    println(allYesAnswers)
    println(onlyYesAnswers)
}
