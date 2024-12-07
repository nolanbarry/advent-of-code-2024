package io.github.nolanbarry.aoc2024.day2

import io.github.nolanbarry.aoc2024.util.Input
import kotlin.math.absoluteValue
import kotlin.math.sign
import kotlin.text.lines

fun main() {
    val result = Input.get("day2/input").lines()
        .map { line ->
            (0 until line.length).map { removeIndex ->
                line
                    .split(Regex("\\s+"))
                    .filterIndexed { i, _ -> removeIndex != i }
                    .map { it.toInt() }
                    .zipWithNext()
                    .map { (a, b) -> a - b }
            }

        }
        .map { it.filter { it.distinctBy { it.sign }.size == 1 } }
        .map { it.filter { it.all { it.absoluteValue in 1..3 } } }
        .filter { it.isNotEmpty() }
        .size

    println(result)
}