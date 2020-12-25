package com.day20

import com.helpers.readData
import kotlin.math.sqrt

typealias Line = List<Char>
typealias Grid = List<Line>

fun flip(input: Grid): Grid {
    return input.reversed()
}

fun rotate(input: Grid): Grid {
    val result = mutableListOf<Line>()
    for (i in input.indices) {
        val line = mutableListOf<Char>()
        for (j in input.indices) {
            line.add(input[input.size - (j + 1)][i])
        }
        result.add(line)
    }
    return result
}

fun findPossibleGrids(input: Grid): List<Grid> {
    val r1 = rotate(input)
    val r2 = rotate(r1)
    val r3 = rotate(r2)
    val f = flip(input)
    val fr1 = rotate(f)
    val fr2 = rotate(fr1)
    val fr3 = rotate(fr2)
    return listOf(input, r1, r2, r3, f, fr1, fr2, fr3)
}

fun isEqual(l1: Line, l2: Line) = l1 == l2

fun findJigsaw(input: List<Pair<Int, Grid>>, jigSaw: List<Grid>, indices: List<Int>): Pair<List<Grid>, List<Int>> {
    for (i in input.indices) {
        val remaining = input.slice(0 until i) + input.slice(i + 1 until input.size)
        for (grid in findPossibleGrids(input[i].second)) {
            if (jigSaw.isEmpty()) {
                val foundJigSaw = findJigsaw(remaining, jigSaw + listOf(grid), indices + listOf(input[i].first))
                if (foundJigSaw.first.size == input.size) {
                    return foundJigSaw
                }
            } else if (isValidJigSawGrid(jigSaw, grid)) {
                val foundJigSaw = findJigsaw(remaining, jigSaw + listOf(grid), indices + listOf(input[i].first))
                if (foundJigSaw.first.isNotEmpty()) {
                    return foundJigSaw
                }
            }
        }
    }
    return Pair(jigSaw, indices)
}

fun isValidJigSawGrid(jigSaw: List<Grid>, grid: Grid): Boolean {
    var isValid = true
    val gridSize = 12
    if (jigSaw.size > gridSize - 1) {
        isValid = isEqual(jigSaw[jigSaw.size - gridSize].last(), grid.first())
    }
    if (jigSaw.size % gridSize != 0) {
        isValid = isValid && isEqual(jigSaw.last().map { it.last() }, grid.map { it.first() })
    }
    return isValid
}

fun main() {
    val input = readData("day20", "data.txt").split("\n\n")
            .map {
                val words = it.split("\n")
                Pair(words[0].subSequence(4, words[0].length - 1).toString().trim().toInt(), words.drop(1).map { it.toList() })
            }
    val result = findJigsaw(input, emptyList(), emptyList())
    val edgeLessImage = removeEdges(result.first)
    findSeaMonsters(createImage(edgeLessImage))
//    val indices = result.second
//    println(indices)
//    val edges = listOf(0, 11, 12 * 11, 143).map { indices[it] }
//    println(edges.map { it.toLong() }.reduce { acc, i -> acc * i })
}

fun removeEdges(input: List<Grid>): List<Grid> {
    return input.map {
        it.drop(1).dropLast(1).map {
            it.drop(1).dropLast(1)
        }
    }
}

fun createImage(input: List<Grid>): Grid {
    return input.chunked(sqrt(input.size.toDouble()).toInt())
            .map {
                it.reduce { acc, list ->
                    acc.mapIndexed { index, line ->
                        line + list[index]
                    }
                }
            }
            .reduce { acc, list ->
                acc + list
            }
}

fun findSeaMonsters(input: Grid) {
    for (image in findPossibleGrids(input)) {
        var totalCoOrds = listOf<Pair<Int, Int>>()
        for (rowIndex in 1 until image.size - 1) {
            for (colIndex in 0..image.size - 20) {
                val coOrds = listOf(
                        Pair(rowIndex - 1, colIndex + 18),
                        Pair(rowIndex, colIndex),
                        Pair(rowIndex, colIndex + 5),
                        Pair(rowIndex, colIndex + 6),
                        Pair(rowIndex, colIndex + 11),
                        Pair(rowIndex, colIndex + 12),
                        Pair(rowIndex, colIndex + 17),
                        Pair(rowIndex, colIndex + 18),
                        Pair(rowIndex, colIndex + 19),
                        Pair(rowIndex + 1, colIndex + 1),
                        Pair(rowIndex + 1, colIndex + 4),
                        Pair(rowIndex + 1, colIndex + 7),
                        Pair(rowIndex + 1, colIndex + 10),
                        Pair(rowIndex + 1, colIndex + 13),
                        Pair(rowIndex + 1, colIndex + 16))
                if (coOrds.all {
                            image[it.first][it.second] == '#'
                        }) {
                    totalCoOrds = totalCoOrds + coOrds
                }
            }
        }
        if (totalCoOrds.isNotEmpty()) {
            println(findWaterRoughness(image,totalCoOrds))
        }
    }
}

fun findWaterRoughness(grid: Grid, coOrds: List<Pair<Int, Int>>): Int {
    val image = grid.map { it.toMutableList() }
    coOrds.forEach {
            image[it.first][it.second] = '.'
    }
    return image.sumBy {
        it.filter {
            it == '#'
        }.size
    }
}