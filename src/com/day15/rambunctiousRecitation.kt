package com.day15

import com.helpers.readData

fun main() {
    val input = readData("day15", "data.txt")
        .split(",")
        .map { it.toInt() }
    val memory = mutableMapOf<Int, MutableList<Int>>()
    var lastTurn = 1
    var lastNum = 0
    input.forEach {
        lastNum = it
        val turns = memory.getOrDefault(it, null)
        if (turns != null){
            turns.add(lastTurn)
        }else {
            memory[it] = mutableListOf(lastTurn)
        }
        lastTurn += 1
    }
    while (lastTurn <= 30000000){
        val turns = memory[lastNum]!!
        lastNum = 0
        if (turns.size > 1){
            lastNum = turns[turns.size-1] - turns[turns.size-2]
        }
        val newTurns = memory.getOrDefault(lastNum, null)
        if (newTurns != null){
            newTurns.add(lastTurn)
        } else {
            memory[lastNum] = mutableListOf(lastTurn)
        }
        lastTurn += 1
    }
    println(lastNum)
}