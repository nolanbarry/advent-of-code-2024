package io.github.nolanbarry.aoc2024.day3

import io.github.nolanbarry.aoc2024.util.Input

fun main() {
    val regex = Regex("mul\\(([0-9]+),([0-9]+)\\)|do\\(\\)|don't\\(\\)")
    var enable = true
    val result = Input.get("day3/input")
        .let { regex.findAll(it) }
        .sumOf {
            if (it.groups[0]!!.value == "do()") enable = true
            else if (it.groups[0]!!.value == "don't()") enable = false
            else if (enable) return@sumOf it.groups[1]!!.value.toInt() * it.groups[2]!!.value.toInt()
            0
        }
    println(result)
}