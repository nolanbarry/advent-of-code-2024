package io.github.nolanbarry.aoc2024.day4

import io.github.nolanbarry.aoc2024.util.Input
import io.github.nolanbarry.aoc2024.util.getOrNull

fun main() {
    val lookingFor = setOf("XMAS", "SAMX")
    val matrix = Input.matrixOfChars("day4/input")
    var count = 0
    matrix.forEachIndexed { y, row ->
        row.forEachIndexed { x, _ ->
            val words = listOf(
                listOfNotNull(
                    matrix.getOrNull(y, x),
                    matrix.getOrNull(y + 1, x),
                    matrix.getOrNull(y + 2, x),
                    matrix.getOrNull(y + 3, x)
                ).joinToString(""),
                listOfNotNull(
                    matrix.getOrNull(y, x),
                    matrix.getOrNull(y, x + 1),
                    matrix.getOrNull(y, x + 2),
                    matrix.getOrNull(y, x + 3)
                ).joinToString(""),
                listOfNotNull(
                    matrix.getOrNull(y, x),
                    matrix.getOrNull(y + 1, x + 1),
                    matrix.getOrNull(y + 2, x + 2),
                    matrix.getOrNull(y + 3, x + 3)
                ).joinToString(""),
                listOfNotNull(
                    matrix.getOrNull(y, x),
                    matrix.getOrNull(y - 1, x + 1),
                    matrix.getOrNull(y - 2, x + 2),
                    matrix.getOrNull(y - 3, x + 3)
                ).joinToString("")
            ).filter { it.isNotEmpty() }
            count += words.count { it in lookingFor }
        }
    }

    println(count)
}