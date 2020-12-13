package com.day11

import com.helpers.readDataAsList

fun main(){
    val input = readDataAsList("day11","data.txt").map {
        it.map { it }
    }
    var copy = input
    val updated = input.map { it.toMutableList() }
    while (true) {
        var changed = 0
        for (x in copy.indices) {
            for (y in copy[x].indices) {
                val ele = copy[x][y]
                if (ele == '.') {
                    continue
                }
                val occupied = getCountOfAdjacentOccupiedSeats(copy, x, y)
                if (ele == 'L' && occupied == 0) {
                    changed += 1
                    updated[x][y] = '#'
                }
                if (ele == '#' && occupied >= 5) {
                    changed += 1
                    updated[x][y] = 'L'
                }
            }
        }
        copy = updated.map { it.toList() }
        if (changed == 0){
            break
        }
    }
    println(findOccupiedSeatsCount(updated))
}

fun findOccupiedSeatsCount(input: List<List<Char>>): Int{
    var occupied = 0
    for (row in input){
        for (seat in row){
            if (seat == '#'){
                occupied += 1
            }
        }
    }
    return occupied
}

fun getCountOfAdjacentOccupiedSeats(input: List<List<Char>>,x: Int, y: Int):Int{
    val adjacentPositions = listOf(
        Pair(-1,-1),
        Pair(-1, 0),
        Pair(-1, 1),
        Pair(0,-1),
        Pair(0, 1),
        Pair(1, -1),
        Pair(1,0),
        Pair(1, 1)
    )

    var occupied = 0
    for (position in adjacentPositions){
        var adjX = position.first + x
        var adjY = position.second + y
        while (adjX in input.indices && adjY in input[adjX].indices){
            if (input[adjX][adjY] == '#'){
                occupied += 1
            }
            if (input[adjX][adjY] != '.'){
                break
            }
            adjX += position.first
            adjY += position.second
        }
    }
    return occupied
}
