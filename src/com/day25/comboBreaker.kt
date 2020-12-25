package com.day25

fun main() {
    val cardPublicKey = 19241437
    val doorPublicKey = 17346587
    println(listOf(listOf(1,2,3)) == listOf(listOf(1,2,3)))
    val cardLoopSize = getLoopSize(cardPublicKey)
    val doorLoopSize = getLoopSize(doorPublicKey)
    println(transform(cardPublicKey.toLong(), doorLoopSize))
    println(transform(doorPublicKey.toLong(),cardLoopSize))
}

fun transform(input: Long, loop: Int): Long {
    var n = 1L
    var i = 0
    while (i != loop){
        n = (n * input) % 20201227
        i += 1
    }
    return n
}

fun getLoopSize(input: Int): Int {
    var n = 1
    var loopSize = 0
    while (n != input){
        n = (n * 7) % 20201227
        loopSize += 1
    }
    return loopSize
}