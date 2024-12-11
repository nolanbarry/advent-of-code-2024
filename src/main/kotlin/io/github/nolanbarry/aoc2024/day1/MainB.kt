package io.github.nolanbarry.aoc2024.day1

import io.github.nolanbarry.aoc2024.util.Input

fun main() {
    val (listA, listB) = Input.get("day1/input").lines()
        .map { it.split(Regex("\\s+")) }
        .map { it.map { it.toInt() } }
        .map { it.let { it[0] to it[1] } }
        .unzip()

    val counts = listB.groupBy { it }.mapValues { (_, value) -> value.size }
    val result = listA.sumOf { it * (counts[it] ?: 0) }
    println(result)
}