package com.day16

import com.helpers.readData

fun parseFields(input: List<String>): List<Field> {
    return input
        .map {
            val words = it.split(":")
            val ranges = words[1]
                .split("or")
                .map { it.trim() }
                .map {
                    val limits = it.split("-").map { it.trim().toInt() }
                    limits[0]..limits[1]
                }
            Field(words[0], ranges)
        }
}


fun main() {
    val input = readData("day16", "data.txt").split("\n\n")
    val fields = parseFields(input[0].split("\n"))
    val myTicket = input[1].split("\n")[1].split(",").map { it.toInt() }
    val nearByTickets = input[2].split("\n").drop(1).map {
        it.split(",").map { it.toInt() }
    }
    val validTickets = nearByTickets.filter {ticket ->
        ticket.all {i ->
            fields.any {
                it.ranges.any {
                    i in it
                }
            }
        }
    }

    val validFields = mutableMapOf<Int,List<Field>>()
    myTicket.forEachIndexed { index, i ->
        validFields[index] = fields.filter {
            it.ranges.any {
                i in it
            }
        }
    }

    validTickets.forEach { ticket ->
        ticket.forEachIndexed { index, i ->
            val remFields = validFields[index]!!
            validFields[index] = remFields.filter {
                it.ranges.any {
                    i in it
                }
            }
        }
    }
    val filteredFields = mutableMapOf<Field, Int>()
    while (filteredFields.size != validFields.size) {
        for (i in validFields) {
            if (i.value.size == 1) {
                filteredFields[i.value[0]] = i.key
            }
            val remFields = validFields[i.key]!!
            validFields[i.key] = remFields.filter {
                !filteredFields.containsKey(it)
            }
        }
    }

    var result = 1L
    for (entry in filteredFields){
        if (entry.key.name.contains("departure")){
            result *= myTicket[entry.value]
        }
    }
    println(result)
}

data class Field(val name: String, val ranges: List<IntRange>)