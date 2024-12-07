package io.github.nolanbarry.aoc2024.day5

import io.github.nolanbarry.aoc2024.util.Input

fun main() {
    val input = Input.get("day5/input-test").lines()

    val rules = input
        .takeWhile { it.trim().isNotEmpty() }
        .map { it.split("|") }
        .groupBy({ it[0] }) { it[1] }
        .mapValues { (_, values) -> values.toSet() }

    mapOf(
        "5" to "1, 2, 3"
    )

    val result = input
        .takeLastWhile { it.trim().isNotEmpty() }
        .map { it.split(",") }
        .filter { printOrder ->
            printOrder
                .runningFold(emptySet<String>()) { acc, nextEl -> acc + setOf(nextEl) }
                .zip(printOrder)
                .all { (previousElements, element) ->
                    previousElements.intersect(rules[element] ?: emptySet()).isEmpty()
                }
        }
        .sumOf { it[it.size / 2].toInt() }

    println(result)
}