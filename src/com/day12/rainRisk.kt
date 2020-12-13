package com.day12

import com.helpers.readDataAsList

fun main() {
    val input = readDataAsList("day12", "data.txt").map {
        Instruction(get(it[0]), it.slice(1 until it.length).toInt())
    }
    val Movement = mutableMapOf(
        Direction.EAST to 0,
        Direction.WEST to 0,
        Direction.SOUTH to 0,
        Direction.NORTH to 0
    )
//part 1
//    var currDirection = Direction.EAST
//    input.forEach {
//        when(it.direction){
//            is Direction -> Movement[it.direction] = Movement[it.direction]!! + it.value
//            is Move -> Movement[currDirection] = Movement[currDirection]!! + it.value
//            is Turn -> currDirection = Direction.getDirection(currDirection, it.direction, it.value)
//            else -> throw Exception("invalid instruction")
//        }
//    }
//    val east = Movement[Direction.EAST]!! - Movement[Direction.WEST]!!
//    val south = Movement[Direction.SOUTH]!! - Movement[Direction.NORTH]!!
//    println(east+south)

    val wayPoint = WayPoint(Direction.EAST,10, Direction.NORTH, 1)
    input.forEach {
        when(it.direction){
            is Direction -> wayPoint.moveWayPoint(it.direction, it.value)
            is Move -> {
                Movement[wayPoint.firstDirection] = Movement[wayPoint.firstDirection]!! + (it.value * wayPoint.firstValue)
                Movement[wayPoint.secondDirection] = Movement[wayPoint.secondDirection]!! + (it.value * wayPoint.secondValue)
            }
            is Turn -> wayPoint.changeWayPoint(it.direction, it.value)
            else -> throw Exception("invalid instruction")
        }
    }
    val east = Movement[Direction.EAST]!! - Movement[Direction.WEST]!!
    val south = Movement[Direction.SOUTH]!! - Movement[Direction.NORTH]!!
    println(east)
    println(south)
    println(east+south)
}

fun get(input: Char): Any =
    when (input) {
        'R' -> Turn.RIGHT
        'L' -> Turn.LEFT
        'F' -> Move.FORWARD
        'E' -> Direction.EAST
        'W' -> Direction.WEST
        'N' -> Direction.NORTH
        'S' -> Direction.SOUTH
        else -> throw Exception("invalid input")
    }

enum class Turn {
    RIGHT,
    LEFT
}

enum class Move {
    FORWARD
}

enum class Direction {
    EAST,
    SOUTH,
    WEST,
    NORTH;

    companion object {
        fun getDirection(direction: Direction, turn: Turn, rotation: Int): Direction {
            val directions = values().toList()
            val reverse = directions.reversed()
            return when (turn) {
                Turn.RIGHT -> directions[(directions.indexOf(direction) + rotation / 90) % 4]
                Turn.LEFT -> reverse[(reverse.indexOf(direction) + rotation / 90) % 4]
            }
        }
    }
}

data class WayPoint(var firstDirection: Direction, var firstValue: Int, var secondDirection: Direction, var secondValue: Int){
    fun moveWayPoint(direction: Direction, value: Int){
        when(direction){
            firstDirection -> firstValue += value
            Direction.getDirection(firstDirection,Turn.RIGHT, 180) -> firstValue -= value
            secondDirection -> secondValue += value
            Direction.getDirection(secondDirection,Turn.RIGHT, 180) -> secondValue -= value
            else -> throw Exception("invalid direction in move way point")
        }
    }

    fun changeWayPoint(turn: Turn, value: Int){
        firstDirection = Direction.getDirection(firstDirection, turn, value)
        secondDirection = Direction.getDirection(secondDirection, turn, value)
    }
}

data class Instruction(val direction: Any, val value: Int)