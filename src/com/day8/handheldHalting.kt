package com.day8

import com.helpers.readDataAsList

fun main() {
    val input = readDataAsList("day8", "data.txt")
        .map {
            val words = it.split(" ")
            Instruction(Command.get(words[0]), words[1].toInt())
        }
    var index = 0
    var acc = 0
    while (index < input.size) {
        val instruction = input[index]
        var copy = input.toMutableList()
        if (instruction.command == Command.NOP) {
            copy[index] = Instruction(Command.JMP, instruction.value)
        }
        if (instruction.command == Command.JMP) {
            copy[index] = Instruction(Command.NOP, instruction.value)
        }
        if (isTerminating(copy.toList())) {
            acc = getValue(copy.toList())
            break
        }
        index += 1
    }
    println(acc)
}

fun isTerminating(input: List<Instruction>): Boolean {
    var index = 0
    val ranInstructions = mutableListOf<Int>()
    while (index < input.size) {
        if (ranInstructions.contains(index)) {
            return false
        }
        ranInstructions.add(index)
        val instruction = input[index]
        if (instruction.command == Command.JMP) {
            index += instruction.value
            continue
        }
        index += 1
    }
    return true
}

fun getValue(input: List<Instruction>): Int {
    var index = 0
    var acc = 0
    val ranInstructions = mutableListOf<Int>()
    while (index < input.size) {
        if (ranInstructions.contains(index)) {
            break
        }
        ranInstructions.add(index)
        val instruction = input[index]
        if (instruction.command == Command.JMP) {
            index += instruction.value
            continue
        }
        if (instruction.command == Command.ACC) {
            acc += instruction.value
        }
        index += 1
    }
    return acc
}


enum class Command {
    NOP,
    ACC,
    JMP;

    companion object {
        fun get(value: String) =
            when (value) {
                "nop" -> NOP
                "acc" -> ACC
                "jmp" -> JMP
                else -> throw Exception("invalid command")
            }
    }
}


data class Instruction(val command: Command, val value: Int)