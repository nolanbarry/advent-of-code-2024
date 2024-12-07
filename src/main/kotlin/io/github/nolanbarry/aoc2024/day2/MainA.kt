package io.github.nolanbarry.aoc2024.day2

import io.github.nolanbarry.aoc2024.util.Input
import kotlin.math.absoluteValue
import kotlin.math.sign
import kotlin.text.lines

fun main() {
    val result = Input.get("day2/input").lines()
        .map { line ->
            line
                .split(Regex("\\s+"))
                .map { it.toInt() }
                .zipWithNext()
                .map { (a, b) -> a - b }
        }
        .filter { it.distinctBy { it.sign }.size == 1 }
        .filter { it.all { it.absoluteValue in 1..3 } }
        .size

    println(result)
}