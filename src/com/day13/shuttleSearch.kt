package com.day13

import com.helpers.readDataAsList

fun main() {
    val input = readDataAsList("day13", "data.txt")
    val earlierTimeStamp = input[0].toLong()
    val busIds = input[1].split(",")
        .mapIndexed { index, s -> Pair(s,index) }
        .filter { it.first != "x" }.map { BusId(it.first.toLong(),it.second.toLong()) }
    val result = busIds.map { findTimeStamp(it.busId, earlierTimeStamp) }.sortedBy {
        it.timeStamp
    }
    //part 1
    println(result[0])

    //part 2
    println(
        busIds.reduce(::findEarlierTimeStamp)
    )
}

fun findTimeStamp(busId: Long, earlierTimeStamp: Long): TimeStamp {
    val rem = earlierTimeStamp % busId
    if (rem == 0L) {
        return TimeStamp(busId, earlierTimeStamp)
    }
    return TimeStamp(busId, earlierTimeStamp + busId - rem)
}


fun findEarlierTimeStamp(firstBusId: BusId, secondBusId: BusId): BusId{
    var start = firstBusId.index
    while ((start+secondBusId.index)%secondBusId.busId != 0L){
        start += firstBusId.busId
    }
    return BusId(firstBusId.busId* secondBusId.busId, start)
}

data class BusId(val busId: Long, val index: Long)
data class TimeStamp(val busId: Long, val timeStamp: Long)