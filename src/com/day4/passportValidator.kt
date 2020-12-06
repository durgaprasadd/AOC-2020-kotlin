package com.day4

import com.helpers.readData
import java.util.regex.Pattern

fun main() {
    val input = readData("day4", "data.txt")
    val passports = parsePassports(input)
    println(passports.filter { validatePassport(it) }.size)
    println(passports.filter { validatePassportWithValidValues(it) }.size)

}

fun validatePassport(passport: Map<String, String>): Boolean {
    val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    return requiredFields.all { passport.containsKey(it) }
}

fun validatePassportWithValidValues(passport: Map<String, String>): Boolean {
    return validatePassport(passport)
            && validateByr(passport)
            && validateIyr(passport)
            && validateEyr(passport)
            && validatePid(passport)
            && validateEcl(passport)
            && validateHcl(passport)
            && validateHgt(passport)
}

fun validateHgt(passport: Map<String, String>): Boolean {
    val hgt = passport["hgt"] as String
    if (hgt.endsWith("in")) {
        val height = hgt.slice(0 until hgt.length - 2).toInt()
        return height in 59..76
    }
    if (hgt.endsWith("cm")) {
        val height = hgt.slice(0 until hgt.length - 2).toInt()
        return height in 150..193
    }
    return false
}

fun validateHcl(passport: Map<String, String>): Boolean {
    val hcl = passport["hcl"] as String
    val validValues = "0123456789abcdef"
    return hcl.startsWith("#") && hcl.length == 7 && hcl.slice(1 until hcl.length).all {
        validValues.contains(it)
    }
}

fun validateEcl(passport: Map<String, String>): Boolean {
    val ecl = passport["ecl"] as String
    val validValues = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    return validValues.contains(ecl)
}

fun validatePid(passport: Map<String, String>): Boolean {
    try {
        val pid = passport["pid"] as String
        val pidNumber = pid.toLong()
        if (pid.length == 9) {
            return true
        }
    } catch (e: Exception) {
        return false
    }
    return false
}

fun validateEyr(passport: Map<String, String>): Boolean {
    try {
        val eyr = (passport["eyr"] as String).toInt()
        if (eyr in 2020..2030) {
            return true
        }
    } catch (e: Exception) {
        return false
    }
    return false
}

fun validateIyr(passport: Map<String, String>): Boolean {
    try {
        val iyr = (passport["iyr"] as String).toInt()
        if (iyr in 2010..2020) {
            return true
        }
    } catch (e: Exception) {
        return false
    }
    return false
}

fun validateByr(passport: Map<String, String>): Boolean {
    try {
        val byr = (passport["byr"] as String).toInt()
        if (byr in 1920..2002) {
            return true
        }
    } catch (e: Exception) {
        return false
    }
    return false
}

fun parsePassports(input: String) =
    input.split("\n\n")
        .map {
            it.split(Pattern.compile("\n| ")).map {
                it.split(":")
            }.fold(mutableMapOf<String, String>()) { acc, ele ->
                acc[ele[0]] = ele[1]
                acc
            }.toMap()
        }

data class Passport(val input: Map<String, String>) {
    val byr by input
    val iyr by input
    val eyr by input
    val hgt by input
    val hcl by input
    val ecl by input
    val pid by input

    fun isValid(): Boolean {
        val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
        return requiredFields.all { input.containsKey(it) }
    }
}
