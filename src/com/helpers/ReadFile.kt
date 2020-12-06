package com.helpers

import java.io.File
import java.nio.file.Paths


fun readDataAsList(dir: String, fileName: String): List<String> {
    val path = Paths.get("").toAbsolutePath().toString()
    val fileReader = File("$path/src/com/$dir/$fileName")
    return fileReader.readLines()
}

fun readData(dir: String, fileName: String): String {
    val path = Paths.get("").toAbsolutePath().toString()
    val fileReader = File("$path/src/com/$dir/$fileName")
    return fileReader.readText()
}

fun readDataAsIntList(dir: String, fileName: String): List<Int> {
    return readDataAsList(dir, fileName).map { it.toInt() }
}

fun readDataAsLongList(dir: String, fileName: String): List<Long> {
    return readDataAsList(dir, fileName).map { it.toLong() }
}