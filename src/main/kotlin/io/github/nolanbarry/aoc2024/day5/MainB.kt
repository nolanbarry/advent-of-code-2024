package io.github.nolanbarry.aoc2024.day5

import io.github.nolanbarry.aoc2024.util.Input
import kotlin.text.toInt

fun main() {
    val input = Input.get("day5/input").lines()

    val rules = input
        .takeWhile { it.trim().isNotEmpty() }
        .map { it.split("|") }
        .groupBy({ it[0] }) { it[1] }
        .mapValues { (_, values) -> values.toSet() }

    val result = input
        .takeLastWhile { it.trim().isNotEmpty() }
        .map { it.split(",") }
        .filter { printOrder ->
            printOrder
                .runningFold(emptySet<String>()) { acc, nextEl -> acc + setOf(nextEl) }
                .zip(printOrder)
                .any { (previousElements, element) ->
                    previousElements.intersect(rules[element] ?: emptySet()).isNotEmpty()
                }
        }
        .map {
            it.sortedWith { a, b ->
                if (b in (rules[a] ?: emptySet())) -1
                else if (a in (rules[b] ?: emptySet())) 1
                else 0
            }
        }
        .sumOf { it[it.size / 2].toInt() }

    println(result)
}