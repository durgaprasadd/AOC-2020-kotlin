package com.codewars

import kotlin.math.max

fun main() {
    println(people(arrayOf(3 to 0,9 to 1,4 to 10,12 to 2,6 to 1,7 to 10)))
}

fun people(busStops: Array<Pair<Int, Int>>) : Int {
    return busStops.fold(0){acc, pair ->
        acc + pair.first - pair.second
    }
}

val concat = (s1:String,s2:String) -> s1 + s2

fun distributionOf(g: IntArray): Pair<Int, Int> {
    var input = g
    var result = Pair(0, 0)
    while (input.isNotEmpty()) {
        if (input.size == 1) {
            result = Pair(result.second, result.first + input[0])
            continue
        }
        if (input.size in 2..3){
            result = Pair(result.second, result.first + max(input.first(),input.last()))
            continue
        }

    }
    return result
}