package com.day3

import com.helpers.readDataAsList

fun findTrees(data: List<String>, right: Int, down: Int): Int {
    var x = 0
    var y = 0
    var count = 0
    while (y < data.size){
        val obj = data[y].get(x%data[y].length)
        if (obj == '#'){
            count += 1
        }
        y += down
        x += right
    }
    return count
}


fun main(){
    val data = readDataAsList("day3","data.txt")
    val first = findTrees(data,1,1).toLong()
    val second = findTrees(data,3,1).toLong()
    val third = findTrees(data,5,1).toLong()
    val fourth = findTrees(data,7,1).toLong()
    val fifth = findTrees(data,1,2).toLong()
    println(first)
    println(second)
    println(third)
    println(fourth)
    println(fifth)
    println(first*second*third*fourth*fifth)
}