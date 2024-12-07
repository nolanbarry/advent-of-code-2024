package io.github.nolanbarry.aoc2024.day3

import io.github.nolanbarry.aoc2024.util.Input

fun main() {
    val result = Input.get("day3/input")
        .let { Regex("mul\\(([0-9]+),([0-9]+)\\)").findAll(it) }
        .sumOf { it.groups[1]!!.value.toInt() * it.groups[2]!!.value.toInt() }
    println(result)
}