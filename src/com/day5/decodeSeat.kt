package com.day5

import com.helpers.readDataAsList

fun main(){
    val input = readDataAsList("day5","data.txt")
    val seatIds = input.map(::getSeatId)
    val max = seatIds.max() as Int
    val min = seatIds.min() as Int
    val sumOfRannge = (max + 1 - min)*(max + min)/2
    val sum = seatIds.sum()
    println(max)
    println(sumOfRannge - sum)
}

fun getRow(seat: String): Int {
    return seat.map {
        if (it == 'F') '0' else '1'
    }.joinToString("").toInt(2)
}

fun getSeatId(seat: String): Int {
    val row = getRow(seat.slice(0..6))
    val column = getColumn(seat.slice(7 until seat.length))
    return (row*8) + column

}

fun getColumn(seat: String): Int {
    return seat.map {
        if (it == 'L') '0' else '1'
    }.joinToString("").toInt(2)
}
