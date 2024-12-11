package io.github.nolanbarry.aoc2024.day6

import io.github.nolanbarry.aoc2024.util.Input
import io.github.nolanbarry.aoc2024.util.coordinatePairs
import io.github.nolanbarry.aoc2024.util.inbounds
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

fun main() {
    val matrix = Input.mutableMatrixOfChars("day6/input")

    var position = matrix.coordinatePairs().first { (y, x) -> matrix[y][x] == '^' }
    var direction = -1 to 0

    while (true) {
        matrix[position.first][position.second] = 'X'
        val nextPosition = (position.first + direction.first) to (position.second + direction.second)
        if (!matrix.inbounds(nextPosition)) break
        if (matrix[nextPosition.first][nextPosition.second] == '#') {
            val newDirectionRadians = (atan2(direction.first.toDouble(), direction.second.toDouble()) + PI / 2)
            direction = sin(newDirectionRadians).roundToInt() to cos(newDirectionRadians).roundToInt()
        } else position = nextPosition
    }

    val result = matrix.flatten().count { it == 'X' }
    println(matrix.joinToString("\n") { it.joinToString("") })
    println(result)
}