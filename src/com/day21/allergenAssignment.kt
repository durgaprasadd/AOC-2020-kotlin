package com.day21

import com.helpers.readDataAsList

fun main() {
    val input = readDataAsList("day21", "data.txt")
            .map {
                val words = it.split("(contains")
                words[0].trim().split(" ").toSet() to words[1].removeSuffix(")").trim().split(", ").toSet()
            }
    val allergens = input.fold(setOf<String>()) { acc, pair ->
        acc + pair.second
    }
    val allergensMap = mutableMapOf<String, Set<String>>()
    allergens.map { allergen ->
        allergensMap[allergen] = input.filter {
            it.second.contains(allergen)
        }.map {
            it.first
        }.reduce { acc, set ->
            acc.intersect(set)
        }
    }
    val singleAllergensMap = mutableSetOf<String>()
    var change = 1
    while (change != 0){
        change = 0
        for (map in allergensMap){
            if(map.value.size == 1){
                singleAllergensMap.add(map.value.first())
                continue
            }
            if (singleAllergensMap.any {  map.value.contains(it)}){
                allergensMap[map.key] = map.value.filter {
                    !singleAllergensMap.contains(it)
                }.toSet()
                change = 1
            }
        }
    }

    val result = input.map { it.first.filter { item ->
        !allergensMap.values.any {
            it.contains(item)
        }
    } }
    println(allergensMap)
    println(result.sumBy { it.size })
    println(allergensMap.keys.sorted().map { allergensMap[it]!!.toList()[0] }.joinToString(","))
}