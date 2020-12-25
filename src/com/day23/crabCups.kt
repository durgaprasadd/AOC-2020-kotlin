package com.day23

fun main() {
    val input = (362981754).toString().split("")
            .filter { it.isNotBlank() }
            .map { it.toInt() }
    val limit = 1000000
    crabCups(addRemainingNumbers(input, limit))
}

fun addRemainingNumbers(input: List<Int>, limit: Int): List<Int> {
    return input + (input.max()!! + 1..limit)
}

fun crabCups(input: List<Int>) {
    var cups = input
    for (i in 1..10) {
        println(cups.subList(0, 10))
        println(cups.reversed().subList(0, 10))
        cups = arrangeCups(cups)
    }
//    println(cups)
}

fun arrangeCups(input: List<Int>): List<Int> {
    val cup = input.first()
    val cups3 = input.slice(1..3)
    val remaining = input.drop(4)
    var destination = cup - 1
    val min = remaining.min()!!
    while (destination >= min) {
        if (remaining.contains(destination)) {
            break
        }
        destination -= 1
    }
    if (destination < min) {
        destination = remaining.max()!!
    }
    val destinationIndex = remaining.indexOf(destination)
    return remaining.slice(0..destinationIndex) + cups3 + remaining.slice(destinationIndex + 1 until remaining.size) + listOf(cup)
}