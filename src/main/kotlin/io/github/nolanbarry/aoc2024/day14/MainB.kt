package io.github.nolanbarry.aoc2024.day14

import io.github.nolanbarry.aoc2024.util.CoordinatePair
import io.github.nolanbarry.aoc2024.util.Input
import io.github.nolanbarry.aoc2024.util.div
import io.github.nolanbarry.aoc2024.util.mutableMatrixOf
import io.github.nolanbarry.aoc2024.util.plus
import io.github.nolanbarry.aoc2024.util.rem
import io.github.nolanbarry.aoc2024.util.times
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

data class Robot(
    var position: CoordinatePair,
    var velocity: CoordinatePair
)

val DIMENSIONS = 101 to 103


fun main(): Unit = runBlocking {
    val input = Input.get("day14/input")
        .lines()
        .map {
            Regex("p=(-?[0-9]+),(-?[0-9]+) v=(-?[0-9]+),(-?[0-9]+)")
                .find(it)!!.groupValues
                .drop(1)
                .map { it.toInt() }
                .run { Robot(get(0) to get(1), get(2) to get(3)) }
        }

    val scores = List(10000) { i ->
        val score = input
            .asSequence()
            .map { robot -> robot.position + (robot.velocity * i) % DIMENSIONS }
            .map { position -> (position + DIMENSIONS) % DIMENSIONS } // Kotlin modulus allows negative results
            .filter { position -> position.first != DIMENSIONS.first / 2 }
            .filter { position -> position.second != DIMENSIONS.second / 2 }
            .groupBy { position -> position * 2 / DIMENSIONS }
            .values
            .fold(1) { acc, next -> acc * next.size }
        score
    }

    printRobots(
        scores.indices
            .zip(scores).sortedBy { (_, score) -> score }
            .first().first
    )
}

fun printRobots(simulateTo: Int) {
    Input.get("day14/input")
        .lines()
        .map {
            Regex("p=(-?[0-9]+),(-?[0-9]+) v=(-?[0-9]+),(-?[0-9]+)")
                .find(it)!!.groupValues
                .drop(1)
                .map { it.toInt() }
                .run { Robot(get(0) to get(1), get(2) to get(3)) }
        }
        .asSequence()
        .map { robot -> robot.position + (robot.velocity * simulateTo) % DIMENSIONS }
        .map { position -> (position + DIMENSIONS) % DIMENSIONS }
        .toSet()
        .also { positions ->
            println(
                mutableMatrixOf(DIMENSIONS.second, DIMENSIONS.first) { row, col ->
                    if ((col to row) in positions) "W" else "."
                }.joinToString("\n") { it.joinToString("") }
            )
        }
    println(simulateTo)
}
