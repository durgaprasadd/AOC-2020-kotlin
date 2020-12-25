package com.day19

import com.helpers.readData

fun getRules(input: List<String>): Map<Int, String> {
    val result = mutableMapOf<Int, String>()
    input.forEach {
        val words = it.split(":")
        result[words[0].toInt()] = words[1].trim()
    }
    return result
}

fun combine(input: List<List<String>>): List<String> {
    if (input.isEmpty()) {
        return emptyList()
    }
    if (input.size == 1) {
        return input[0]
    }
    val result = mutableListOf<String>()
    for (ele1 in input[0]) {
        for (ele2 in input[1]) {
            result.add(ele1 + ele2)
        }
    }
    return result
}

fun getValidString(rules: Map<Int, String>, rule: String): List<String> {
    return when (rule) {
        "\"a\"" -> listOf("a")
        "\"b\"" -> listOf("b")
        else -> rule.split("|").flatMap {
            combine(it.trim().split(" ").map { it.toInt() }.map { getValidString(rules, rules[it]!!) })
        }
    }
}

fun <T> joinTwoLists(first: List<T>, second: List<T>): List<T> {
    val result = mutableListOf<T>()
    result.addAll(first)
    result.addAll(second)
    return result.distinct()
}

fun validateMessage(rules: Map<Int, String>, rule: String, message: String): List<Int> {
    return when (rule) {
        "\"a\"" -> if (message.startsWith("a")) listOf(message.length - 1) else emptyList()
        "\"b\"" -> if (message.startsWith("b")) listOf(message.length - 1) else emptyList()
        else -> {
            val orRules = rule.split("|").map { it.trim() }.filter { it.isNotEmpty() }
            val andRules = orRules[0].split(" ").map { it.toInt() }
            var result:List<Int>? = null
            for (andRule in andRules) {
                if (result == null){
                    result = validateMessage(rules, rules[andRule]!!, message).distinct()
                    continue
                }
                result = result.filter { it != 0 }
                        .flatMap {
                            validateMessage(rules, rules[andRule]!!, message.slice(message.length - it until message.length))
                        }.distinct()
            }
            if (orRules.size == 2) {
                result = joinTwoLists(result!!, validateMessage(rules, orRules[1], message))
            }
            return result!!
        }
    }
}

fun main() {
    val input = readData("day19", "data.txt").split("\n\n")
    val messages = input[1].split("\n")
    val rawRules = getRules(input[0].split("\n"))

    // part 1
//    val result = getValidString(rawRules, rawRules[0]!!)
//    val validMessages = messages.filter {
//        result.contains(it.trim())
//    }
//    println(validMessages.size)

    println(messages.filter {
        validateMessage(rawRules, rawRules[0]!!, it).contains(0)
    }.size)

}