package com.day14

import com.helpers.readDataAsList

fun main() {
    val input = readDataAsList("day14", "data.txt")
    val parsedData = parseData(input)
    val memory = mutableMapOf<Long, Long>()
    println(parsedData)
//    parsedData.forEach {
//        executeMask(it, memory)
//    }
//    println(memory.values.sum())

    val updatedData = parsedData.flatMap { mask ->
        mask.memory.keys.map {
            applyAddressToMask(mask,it, mask.memory[it]!!)
        }
    }
    println(updatedData)
    updatedData.forEach {
        memory.putAll(it.memory)
    }
    println(memory.values.sum())
}

fun applyAddressToMask(mask: Mask, address: Long, value: Long): Mask {
    val updatedBitMask = executeBitMaskForAddress(mask.value, address.toString(2))
    println(updatedBitMask)
    val result = Mask(mask.value)
    getAddresses(updatedBitMask).forEach {
        result.addMemory(it.toLong(2), value)
    }
    return result
}

fun getAddresses(mask: String): List<String> {
    if (!mask.contains('X')) {
        return listOf(mask)
    }

    return getAddresses(mask.replaceFirst('X', '0')) + getAddresses(mask.replaceFirst('X', '1'))
}

fun executeMask(mask: Mask, memory: MutableMap<Long, Long>) {
    mask.memory.keys.forEach {
        memory[it] = executeBitMask(mask.value, mask.memory[it]!!.toString(2))
    }
}

fun executeBitMaskForAddress(mask: String, address: String): String {
    var result = ""
    for (i in mask.indices) {
        val char = mask[mask.length - i - 1]
        if (i < address.length && char == '0') {
            result = address[address.length - i - 1] + result
            continue
        }
        result = char + result
    }
    return result
}

fun executeBitMask(mask: String, value: String): Long {
    var result = ""
    if (mask.length > value.length) {
        for (i in mask.indices) {
            val char = mask[mask.length - i - 1]
            if (char != 'X') {
                result = char + result
                continue
            }
            if (i < value.length) {
                result = value[value.length - i - 1] + result
                continue
            }
            result = '0' + result
        }
    } else {
        for (i in value.indices) {
            if (i < mask.length) {
                val char = mask[mask.length - i - 1]
                if (char != 'X') {
                    result = char + result
                    continue
                }
            }
            result = value[value.length - i - 1] + result
        }
    }
    return result.toLong(2)
}

fun parseData(input: List<String>): List<Mask> {
    val parsedData = mutableListOf<Mask>()
    var currMask: Mask? = null
    for (ele in input) {
        if (ele.contains("mask")) {
            if (currMask != null) {
                parsedData.add(currMask)
            }
            val value = ele.split("=")[1].trim()
            currMask = Mask(value)
            continue
        }
        val words = ele.split("=").map { it.trim() }
        currMask?.addMemory(words[0].slice(4 until words[0].length - 1).toLong(), words[1].toLong())
    }
    if (currMask != null)
        parsedData.add(currMask)
    return parsedData
}

data class Mask(val value: String, val memory: MutableMap<Long, Long> = mutableMapOf()) {
    fun addMemory(position: Long, value: Long) {
        memory[position] = value
    }
}