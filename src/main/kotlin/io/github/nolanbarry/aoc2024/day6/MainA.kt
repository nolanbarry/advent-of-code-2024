package io.github.nolanbarry.aoc2024.day6

import io.github.nolanbarry.aoc2024.util.Input
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

fun main() {
    val map = Input.get("day6/input").lines()
        .map { it.trim().split("").filter { it.isNotEmpty() }.toMutableList() }

    val (height, width) = map.size to map.first().size

    var position = sequence {
        for (y in 0 until height) for (x in 0 until width) yield(y to x)
    }.first { (y, x) -> map[y][x] == "^" }
    var direction = -1 to 0

    while (true) {
        map[position.first][position.second] = "X"
        val nextPosition = (position.first + direction.first) to (position.second + direction.second)
        if (nextPosition.first !in 0 until height || nextPosition.second !in 0 until width) break
        if (map[nextPosition.first][nextPosition.second] == "#") {
            val newDirectionRadians = (atan2(direction.first.toDouble(), direction.second.toDouble()) + PI / 2)
            direction = sin(newDirectionRadians).roundToInt() to cos(newDirectionRadians).roundToInt()
        } else position = nextPosition
    }

    val result = map.flatten().count { it == "X" }
    println(map.joinToString("\n") { it.joinToString("") })
    println(result)
}