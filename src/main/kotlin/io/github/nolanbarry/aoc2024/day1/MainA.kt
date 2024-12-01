package io.github.nolanbarry.aoc2024.day1

import io.github.nolanbarry.aoc2024.util.Input
import kotlin.math.absoluteValue

fun main() {
    val result = Input.get("day1/input").lines()
        .map { it.split(Regex("\\s+")) }
        .map { it.map { it.toInt() } }
        .map { it.let { it[0] to it[1] } }
        .unzip()
        .let { (first, second) -> first.sorted() to second.sorted() }
        .let { (first, second) -> first.zip(second) }
        .sumOf { (a, b) -> (a - b).absoluteValue }
    println(result)
}