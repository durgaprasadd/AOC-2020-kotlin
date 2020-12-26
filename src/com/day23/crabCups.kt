package com.day23

fun main() {
    val input = (362981754).toString().split("")
            .filter { it.isNotBlank() }
            .map { it.toInt() }
    val limit = 1000000
    val actualInput = addRemainingNumbers(input, limit)
    val cups = Array<Int>(1000001) { 0 }
    actualInput.forEachIndexed { index, i ->
        cups[i] = actualInput[(index + 1) % actualInput.size]
    }

    var move = 0
    var currentCup = actualInput[0]
    val max = listOf(limit, limit - 1, limit - 2, limit - 3)
    while (move < 10000000) {
        val cup1 = cups[currentCup]
        val cup2 = cups[cup1]
        val cup3 = cups[cup2]
        var destination = currentCup - 1
        val removedCups = listOf(currentCup, cup1, cup2, cup3)
        while (destination > 0) {
            if (cups[destination] != 0 && !removedCups.contains(destination)) {
                break
            }
            destination--
        }
        if (destination == 0) {
            destination = max.first {
                !removedCups.contains(it)
            }
        }
        val temp = cups[destination]
        cups[currentCup] = cups[cup3]
        cups[destination] = cup1
        cups[cup3] = temp
        currentCup = cups[currentCup]
        move++
    }
    println(cups[1].toLong() * cups[cups[1]].toLong())
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