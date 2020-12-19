package com.day17

import com.helpers.readDataAsList

fun getCombinations(input: List<Int>, n: Int = input.size): List<List<Int>> {
    if (n == 0) {
        return emptyList()
    }
    if (n == 1) {
        return input.map { listOf(it) }
    }
    val combinations = getCombinations(input, n - 1)
    var result = emptyList<List<Int>>()
    for (ele in input) {
        result = combinations.map {
            it.plus(ele)
        }.plus(result)
    }
    return result
}

fun main() {
    val positions = listOf(-1, 0, 1)
    val neighbourPositions = getCombinations(positions, 4)
        .filterNot {
            it.all { it == 0 }
        }.map {
            Position(it[0], it[1], it[2], it[3])
        }
    val input = listOf(listOf(readDataAsList("day17", "data.txt")
        .map {
            it.toList()
        }
    ))
    var cycles = 0
    var previous = input
    while (cycles < 6) {
        val updatedData = mutableListOf<List<List<List<Char>>>>()
        for (w in -1..previous.size) {
            val updatedCube = mutableListOf<List<List<Char>>>()
            for (z in -1..previous[0].size) {
                val updatedGrid = mutableListOf<List<Char>>()
                for (y in -1..previous[0][0].size) {
                    val updatedLine = mutableListOf<Char>()
                    for (x in -1..previous[0][0][0].size) {
                        val position = Position(x, y, z, w)
                        val validNeighbourPositions = getValidNeighbourPositions(previous, neighbourPositions, position)
                        val activeNeighboursCount = getActiveNeighboursCount(previous, validNeighbourPositions)
                        if (!position.isValid(previous) || previous[w][z][y][x] == '.') {
                            if (activeNeighboursCount == 3) {
                                updatedLine.add('#')
                            } else {
                                updatedLine.add('.')
                            }
                        } else if (previous[w][z][y][x] == '#' && activeNeighboursCount in 2..3) {
                            updatedLine.add('#')
                        } else {
                            updatedLine.add('.')
                        }
                    }
                    updatedGrid.add(updatedLine)
                }
                updatedCube.add(updatedGrid)
            }
            updatedData.add(updatedCube)
        }
        previous = updatedData
        cycles += 1

    }
    println(getActiveFourthDimensionCount(previous))
}

fun printCube(cube: List<List<List<Char>>>) {
    cube.forEachIndexed { index, list ->
        println("\n$index")
        list.forEach { println(it.joinToString("")) }
    }
}

data class Position(val x: Int, val y: Int, val z: Int, val w: Int) {

    fun addPosition(position: Position): Position {
        return Position(this.x + position.x, this.y + position.y, this.z + position.z, this.w + position.w)
    }

    fun isValid(): Boolean {
        return x >= 0 && y >= 0 && z >= 0 && w >= 0
    }

    fun isValid(cube: List<List<List<List<Char>>>>): Boolean {
        return isValid() && w < cube.size && z < cube[w].size && y < cube[w][z].size && x < cube[w][z][y].size
    }
}

fun getActiveFourthDimensionCount(cube: List<List<List<List<Char>>>>): Int {
    var count = 0
    for (grid in cube) {
        count += getActiveCubeCount(grid)
    }
    return count
}

fun getActiveCubeCount(cube: List<List<List<Char>>>): Int {
    var count = 0
    for (grid in cube) {
        for (line in grid) {
            for (cell in line) {
                if (cell == '#') {
                    count += 1
                }
            }
        }
    }
    return count
}

fun getActiveNeighboursCount(cube: List<List<List<List<Char>>>>, neighbourPositions: List<Position>): Int {
    var count = 0
    for (position in neighbourPositions) {
        if (cube[position.w][position.z][position.y][position.x] == '#') {
            count += 1
        }
    }
    return count
}

fun getValidNeighbourPositions(
    cube: List<List<List<List<Char>>>>,
    neighbourPositions: List<Position>,
    position: Position
): List<Position> {
    return neighbourPositions
        .map {
            it.addPosition(position)
        }
        .filter {
            it.isValid(cube)
        }
}
