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

fun recursiveCombat(player1: List<Int>, player2: List<Int>, rounds: List<Pair<List<Int>, List<Int>>>): Int {
    if (player1.isEmpty()) {
        return 2
    }
    if (player2.isEmpty()) {
        return 1
    }
    if (rounds.any {
                it == Pair(player1, player2)
            }) {
        return 1
    }
    val player1Card = player1.first()
    val player2Card = player2.first()
    var won: Int? = null
    if (player1Card < player1.size && player2Card < player2.size) {
        won = recursiveCombat(player1.drop(1), player2.drop(1), emptyList())
    }
    return if (won == 1 || (player1Card > player2Card && won == null)) recursiveCombat(player1.drop(1) + listOf(player1Card, player2Card), player2.drop(1), rounds + listOf(Pair(player1, player2))) else recursiveCombat(player1.drop(1), player2.drop(1) + listOf(player2Card, player1Card), rounds + listOf(Pair(player1, player2)))
}

fun recurseCombat(player1: List<Int>, player2: List<Int>, rounds: List<Pair<List<Int>, List<Int>>>): List<Int> {
    if (player1.isEmpty()) {
        return player2
    }
    if (player2.isEmpty()) {
        return player1
    }
    var won: Int? = null
    if (rounds.any {
                it == Pair(player1, player2)
            }) {
        return player1
    }
    val player1Card = player1.first()
    val player2Card = player2.first()
    if (player1Card < player1.size && player2Card < player2.size) {
        won = recursiveCombat(player1.drop(1), player2.drop(1), emptyList())
    }
    return if (won == 1 || (player1Card > player2Card && won == null)) recurseCombat(player1.drop(1) + listOf(player1Card, player2Card), player2.drop(1), rounds + listOf(Pair(player1, player2))) else recurseCombat(player1.drop(1), player2.drop(1) + listOf(player2Card, player1Card), rounds + listOf(Pair(player1, player2)))
}

fun main() {
    val input = readData("day22", "data.txt").split("\n\n").map {
        it.split("\n").drop(1).map { it.toInt() }
    }
//    println(recurseCombat(input[0], input[1], emptyList()).reversed().mapIndexed { index, i -> (i * (index + 1)).toLong() }.sum())
    println(recursiveCombat(Player(1,input[0]), Player(2,input[1])).deck.reversed().mapIndexed { index, i ->  (i * (index + 1)).toLong()}.sum())
}

fun recursiveCombat(player1: Player, player2: Player): Player {
    val rounds = mutableListOf<Pair<List<Int>, List<Int>>>()
    while (player1.deck.isNotEmpty() && player2.deck.isNotEmpty()) {
        if (rounds.any { it == Pair(player1.deck, player2.deck) }){
            return player1
        }
        rounds.add(Pair(player1.deck,player2.deck))
        val player1Card = player1.deck.first()
        val player2Card = player2.deck.first()
        if (player1Card < player1.deck.size && player2Card < player2.deck.size){
            val won = recursiveCombat(player1.removeCards(player1Card),player2.removeCards(player2Card))
            if (won.id == 1){
                player1.wonRound(listOf(player1Card,player2Card))
                player2.loseRound()
                continue
            }
            player2.wonRound(listOf(player2Card,player1Card))
            player1.loseRound()
            continue
        }
        if (player1Card > player2Card){
            player1.wonRound(listOf(player1Card,player2Card))
            player2.loseRound()
            continue
        }
        player2.wonRound(listOf(player2Card,player1Card))
        player1.loseRound()
    }
    if (player1.deck.isEmpty()){
        return player2
    }
    return player1
}

data class Player(val id: Int, var deck: List<Int>){
    fun removeCards(card: Int): Player {
        return this.copy(deck = deck.drop(1).slice(0 until card))
    }

    fun wonRound(cards: List<Int>){
        this.deck = deck.drop(1) + cards
    }

    fun loseRound(){
        this.deck = deck.drop(1)
    }
}