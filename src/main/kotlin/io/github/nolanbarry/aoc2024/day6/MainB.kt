package io.github.nolanbarry.aoc2024.day6

import io.github.nolanbarry.aoc2024.util.Input
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

fun main() {
    val startingMap = Input.get("day6/input").lines()
        .map { it.trim().split("").filter { it.isNotEmpty() }.toMutableList() }

    val (height, width) = startingMap.size to startingMap.first().size

    var position = sequence {
        for (y in 0 until height) for (x in 0 until width) yield(y to x)
    }.first { (y, x) -> startingMap[y][x] == "^" }
    var direction = -1 to 0

    val potentialObstructions = mutableSetOf<Pair<Int, Int>>()
    val obstructionConsidered = mutableSetOf<Pair<Int, Int>>()

    fun step(
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
        map: List<List<String>>
    ): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        return if (!(position + direction).inbounds(height, width)) return (position + direction) to direction
        else if (map[position + direction] == "#") position to direction.rotate90()
        else (position + direction) to direction
    }

    fun obstructionCausesLoop(obstruction: Pair<Int, Int>): Boolean {
        if (startingMap[obstruction] == "#") return false
        val visited = mutableMapOf<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>()
        var position = position
        var direction = direction
        val map = startingMap.map { it.toMutableList() }.also { it[obstruction] = "#" }
        while (position.inbounds(height, width) && direction !in (visited[position] ?: emptySet())) {
            visited.computeIfAbsent(position) { mutableSetOf() }.add(direction)
            val (p, d) = step(position, direction, map)
            position = p; direction = d
        }
        return position.inbounds(height, width)
    }

    while ((position + direction).inbounds(height, width)) {
        if (position + direction !in obstructionConsidered) {
            if (obstructionCausesLoop(position + direction)) {
                potentialObstructions.add(position + direction)
                startingMap[position + direction] = "O"
            }
            obstructionConsidered.add(position + direction)
        }

        val (p, d) = step(position, direction, startingMap)
        position = p; direction = d
    }

    println(startingMap.joinToString("\n") { it.joinToString("") })
    println(potentialObstructions.size)
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = (first + other.first) to (second + other.second)
fun Pair<Int, Int>.rotate90(): Pair<Int, Int> {
    val newDirectionRadians = (atan2(first.toDouble(), second.toDouble()) + PI / 2)
    return sin(newDirectionRadians).roundToInt() to cos(newDirectionRadians).roundToInt()
}

fun Pair<Int, Int>.inbounds(height: Int, width: Int) = first in 0 until height && second in 0 until width
operator fun List<List<String>>.get(coords: Pair<Int, Int>) = this[coords.first][coords.second]
operator fun List<MutableList<String>>.set(coords: Pair<Int, Int>, value: String) {
    this[coords.first][coords.second] = value
}