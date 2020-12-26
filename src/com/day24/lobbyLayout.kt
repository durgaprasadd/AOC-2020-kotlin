package com.day24

import com.helpers.readDataAsList

fun main() {
    val input = readDataAsList("day24", "data.txt")
    val result = mutableMapOf<Pair<Double, Double>, Int>()
    input.map { getCoords(it) }.forEach {
        result.compute(it) { x, y ->
            if (y != null) {
                (y + 1) % 2
            } else 1
        }
    }
    println(result.values.sum())
    var blackTilesResult = result.entries.filter {
        it.value == 1
    }.map {
        it.key
    }.toSet()
    var day = 0
    while (day < 100) {
        val blackTiles = mutableSetOf<Pair<Double, Double>>()
        for (tile in blackTilesResult) {
            val neighbors = getNeighbors(tile.first, tile.second)
            val blackNeighborsCount = neighbors.sumBy {
                if (blackTilesResult.contains(it))
                    1
                else
                    0
            }
            if (blackNeighborsCount == 1 || blackNeighborsCount == 2) {
                blackTiles.add(tile)
            }
            neighbors.filter { !blackTilesResult.contains(it) }
                    .forEach {
                        val neighborsOfWhite = getNeighbors(it.first, it.second)
                        val blackNeighborsCountOfWhite = neighborsOfWhite.sumBy {
                            if (blackTilesResult.contains(it))
                                1
                            else
                                0
                        }
                        if (blackNeighborsCountOfWhite == 2) {
                            blackTiles.add(it)
                        }

                    }
        }
        blackTilesResult = blackTiles
        day++
    }
    println(blackTilesResult.size)
}

fun getCoords(_input: String): Pair<Double, Double> {
    var input = _input
    var row = 0.toDouble()
    var col = 0.toDouble()
    while (input.isNotBlank()) {
        when {
            input.startsWith("se") -> {
                input = input.replaceFirst("se", "")
                row++
                col += 0.5
            }
            input.startsWith("sw") -> {
                input = input.replaceFirst("sw", "")
                row++
                col -= 0.5
            }
            input.startsWith("ne") -> {
                input = input.replaceFirst("ne", "")
                row--
                col += 0.5
            }
            input.startsWith("nw") -> {
                input = input.replaceFirst("nw", "")
                row--
                col -= 0.5
            }
            input.startsWith("e") -> {
                input = input.replaceFirst("e", "")
                col += 1
            }
            input.startsWith("w") -> {
                input = input.replaceFirst("w", "")
                col -= 1
            }
        }
    }
    return Pair(row, col)
}

fun getNeighbors(row: Double, col: Double): List<Pair<Double, Double>> {
    return listOf(
            Pair(row, col + 1),
            Pair(row, col - 1),
            Pair(row - 1, col + 0.5),
            Pair(row - 1, col - 0.5),
            Pair(row + 1, col + 0.5),
            Pair(row + 1, col - 0.5)
    )
}