package com.problem684

fun main() {
    var curr = 1L
    var next = 2L
    var res = 0L
    var lastRes = 0L
    var lastN = 0L
    for (i in 1..90) {
        println(i)
        lastRes = S(curr, lastN, lastRes)
        lastN = curr
        res += lastRes
        val temp = next
        next += curr
        curr = temp
    }
    println(res)
}

fun S(n: Long, lastN: Long, lastRes: Long): Long {
    var result = lastRes
    for (i in lastN + 1..n) {
        result += s(i)
    }
    return result
}

fun s(n: Long): Long {
    return n
}