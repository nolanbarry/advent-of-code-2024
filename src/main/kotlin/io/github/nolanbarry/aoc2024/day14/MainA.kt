package io.github.nolanbarry.aoc2024.day14

import io.github.nolanbarry.aoc2024.util.Input
import io.github.nolanbarry.aoc2024.util.LongCoordinatePair
import io.github.nolanbarry.aoc2024.util.div
import io.github.nolanbarry.aoc2024.util.plus
import io.github.nolanbarry.aoc2024.util.rem
import io.github.nolanbarry.aoc2024.util.times

fun main() {
    data class Robot(
        var position: LongCoordinatePair,
        var velocity: LongCoordinatePair
    )

    val dimensions = 101L to 103L
    val steps = 100L

    val result = Input.get("day14/input")
        .lines()
        .map {
            Regex("p=(-?[0-9]+),(-?[0-9]+) v=(-?[0-9]+),(-?[0-9]+)")
                .find(it)!!.groupValues
                .drop(1)
                .map { it.toLong() }
                .run { Robot(get(0) to get(1), get(2) to get(3)) }
        }
        .asSequence()
        .map { robot -> robot.position + (robot.velocity * steps) % dimensions }
        .map { position -> (position + dimensions) % dimensions } // Kotlin modulus allows negative results
        .filter { position -> position.first != dimensions.first / 2 }
        .filter { position -> position.second != dimensions.second / 2 }
        .groupBy { position -> position * 2L / dimensions }
        .values
        .also { println(it.joinToString("\n")) }
        .fold(1) { acc, next -> acc * next.size }

    println(result)
}
