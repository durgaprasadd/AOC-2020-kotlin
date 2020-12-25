package com.day22

import com.helpers.readData

fun combat(player1: List<Int>, player2: List<Int>): List<Int> {
    if (player1.isEmpty()) {
        return player2
    }
    if (player2.isEmpty()) {
        return player1
    }
    val player1Card = player1.first()
    val player2Card = player2.first()
    return if (player1Card > player2Card) combat(player1.drop(1) + listOf(player1Card, player2Card), player2.drop(1)) else combat(player1.drop(1), player2.drop(1) + listOf(player2Card, player1Card))
}

fun recursiveCombat(player1: List<Int>, player2: List<Int>, player1Rounds: List<List<Int>>,player2Rounds: List<List<Int>>): Int {
    if (player1.isEmpty()) {
        return 2
    }
    if (player2.isEmpty()) {
        return 1
    }
    if (player1Rounds.any {
                it == player1
            } || player2Rounds.any {
                it == player2
            }) {
        return 1
    }
    val player1Card = player1.first()
    val player2Card = player2.first()
    var won:Int? = null
    if (player1Card < player1.size && player2Card < player2.size) {
        won =  recursiveCombat(player1.drop(1), player2.drop(1), emptyList(), emptyList())
    }
    return if (won == 1 || (player1Card > player2Card && won == null)) recursiveCombat(player1.drop(1) + listOf(player1Card, player2Card), player2.drop(1), player1Rounds + listOf(player1), player2Rounds + listOf(player2)) else recursiveCombat(player1.drop(1), player2.drop(1) + listOf(player2Card, player1Card), player1Rounds + listOf(player1), player2Rounds + listOf(player2))
}

fun recurseCombat(player1: List<Int>, player2: List<Int>, rounds: List<Pair<List<Int>,List<Int>>>): List<Int> {
    if (player1.isEmpty()) {
        return player2
    }
    if (player2.isEmpty()) {
        return player1
    }
    var won:Int? = null
    if (rounds.any {
                it == player1
            } || player2Rounds.any {
                it == player2
            }) {
        return player1
    }
    val player1Card = player1.first()
    val player2Card = player2.first()
    if (player1Card < player1.size && player2Card < player2.size) {
        won = recursiveCombat(player1.drop(1), player2.drop(1), emptyList(), emptyList())
    }
    return if (won == 1 || (player1Card > player2Card && won == null)) recurseCombat(player1.drop(1) + listOf(player1Card, player2Card), player2.drop(1), player1Rounds + listOf(player1), player2Rounds + listOf(player2)) else recurseCombat(player1.drop(1), player2.drop(1) + listOf(player2Card, player1Card), player1Rounds + listOf(player1), player2Rounds + listOf(player2))
}

fun main() {
    val input = readData("day22", "data.txt").split("\n\n").map {
        it.split("\n").drop(1).map { it.toInt() }
    }
    println(recurseCombat(input[0], input[1], emptyList(), emptyList()).reversed().mapIndexed { index, i -> (i * (index + 1)).toLong() }.sum())
}