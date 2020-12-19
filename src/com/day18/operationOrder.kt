package com.day18

import com.helpers.readDataAsList

fun main() {
    val input = readDataAsList("day18", "data.txt")
    println(input.map {
        solveInstruction(it)
    }.sum())
//    println(solveExpressions2("1 + 2 * 3"))
}
//16561915796

fun solveInstruction(input: String): Long {
    var updatedInstruction = input
    while (updatedInstruction.contains(")")) {
        val last = updatedInstruction.indexOf(')')
        val sub = updatedInstruction.subSequence(0, last)
        val first = sub.lastIndexOf('(')
        updatedInstruction = updatedInstruction.slice(0 until first) + solveExpressions2(
            sub.subSequence(first + 1, sub.length).toString()
        ) + updatedInstruction.slice(last+1 until updatedInstruction.length)
    }
    return solveExpressions2(updatedInstruction)
}

fun solveExpression(input: String): Long {
    val parsedData = input.split(" ").map { it.trim() }
    var result = parsedData[0].toLong()
    var index = 1
    while (index < parsedData.size) {
        when (parsedData[index]) {
            "+" -> result += parsedData[index + 1].toLong()
            "*" -> result *= parsedData[index + 1].toLong()
        }
        index += 2
    }
    return result
}

fun solveExpressions2(input: String): Long {
    return input.split("*").map {
        it.trim()
    }
        .map {
            it.split("+").map { it.trim() }.map {
                it.toLong()
            }.sum()
        }.reduce { acc, l ->
            acc * l
        }
}