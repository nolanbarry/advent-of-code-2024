package io.github.nolanbarry.aoc2024.day6

import io.github.nolanbarry.aoc2024.util.Input

fun main() {
    val map = Input.get("day6/input-test").lines()
        .map { it.split("") }

    val (height, width) = map.size to map.first().size

    val guardPosition = sequence {
        for (y in 0 until height) for (x in 0 until width) yield(y to x)
    }.first { (y, x) -> map[y][x] == "^" }
}