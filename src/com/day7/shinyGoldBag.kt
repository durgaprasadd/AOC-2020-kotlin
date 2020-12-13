package com.day7

import com.helpers.readDataAsList

fun main() {
    val input = readDataAsList("day7", "data.txt")
    parseData(input)
}

fun getColorBag(input: String): String {
    return input.split(" ")
        .filter {
            !it.trim().all { char -> char.isDigit() }
        }
        .map {
            if (it.endsWith(".")) {
                it.slice(0 until it.length - 1)
            } else it
        }
        .map {
            if (it.endsWith("bags")) {
                it.slice(0 until it.length - 1)
            } else it
        }.joinToString(" ")
}
fun getColorBagWithNumber(input: String): Pair<String, String> {
    val words = input.split(" ")
        .map {
            it.trim()
        }
        .filter {
            it.isNotEmpty()
        }
        .map {
            if (it.endsWith(".")) {
                it.slice(0 until it.length - 1)
            } else it
        }
        .map {
            if (it.endsWith("bags")) {
                it.slice(0 until it.length - 1)
            } else it
        }
    return words[0] to words.slice(1 until words.size).joinToString(" ")
}

fun parseData(input: List<String>) {
    val parsedData = input
        .map {
            val data = it.split("contain").map { it.trim() }
            if (data[1] == "no other bags.") {
                getColorBag(data[0]) to emptyList()
            } else {
                getColorBag(data[0]) to data[1].split(",").map { getColorBagWithNumber(it) }
            }
        }
//        .filter {
//            it.first != "shiny gold bag"
//        }
        .fold(emptyMap<String, List<Pair<String, String>>>()) { acc, pair ->
            acc.plus(pair)
        }
//    println(
//        parsedData.keys.filter {
//            canContainGoldBag(it, parsedData)
//        }.size
//    )
    println(canContainNumberOfBags("shiny gold bag", parsedData))
}

fun canContainNumberOfBags(bag: String, data: Map<String, List<Pair<String,String>>>): Int {
    println(bag)
    val list = data[bag] as List
    return list.fold(0){ acc, pair ->
        println(pair)
        acc + (pair.first.toInt() + (pair.first.toInt()* canContainNumberOfBags(pair.second, data)))
    }
}

//fun canContainGoldBag(bag: String, data: Map<String, List<Pair<String,String>>): Boolean {
//    val list = data[bag] as List
//    return list.any{ it == "shin"} || list.any { canContainGoldBag(it, data) }
//}
