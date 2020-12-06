package com.day2

import com.helpers.readDataAsList

fun readAndParseData() =
    readDataAsList("day2","data.txt").map {
        val data = it.split(" ")
        val limits = data[0].split("-").map { it.toInt() }
        val letter = data[1].get(0)
        val password = data[2]
        mapOf(
            "limits" to limits,
            "letter" to letter,
            "password" to password
        )
    }

fun validatePassword(input: Map<String,Any>):Boolean{
    val password = input.get("password").toString()
    val letter = input.get("letter") as Char
    val limits = input.get("limits") as List<Int>
    val count = password.count {
        it.equals(letter)
    }
    return limits[0] <= count && count <= limits[1]
}

fun validatePasswordWithRule2(input: Map<String,Any>):Boolean{
    val password = input.get("password").toString()
    val letter = input.get("letter") as Char
    val limits = input.get("limits") as List<Int>
    val one = password.getOrNull(limits[0]-1) == letter
    val two = password.getOrNull(limits[1]-1) == letter
    return (one || two) && !(one && two)
}

fun main(){
    val parsedData = readAndParseData()
    println(parsedData.filter { validatePassword(it) }.size)
    println(parsedData.filter { validatePasswordWithRule2(it) }.size)
}