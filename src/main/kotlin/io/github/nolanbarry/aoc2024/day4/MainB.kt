package io.github.nolanbarry.aoc2024.day4

import io.github.nolanbarry.aoc2024.util.Input

fun main() {
    val lookingFor = setOf("MSAMS", "MMASS", "SMASM", "SSAMM")
    val matrix = Input.matrixOfChars("day4/input")
    var count = 0
    matrix.forEachIndexed { y, row ->
        row.forEachIndexed { x, _ ->
            val words = listOf(
                listOfNotNull(
                    matrix.getOrNull(y)?.getOrNull(x),
                    matrix.getOrNull(y)?.getOrNull(x + 2),
                    matrix.getOrNull(y + 1)?.getOrNull(x + 1),
                    matrix.getOrNull(y + 2)?.getOrNull(x),
                    matrix.getOrNull(y + 2)?.getOrNull(x + 2),
                ).joinToString(""),
            ).filter { it.isNotEmpty() }
            println(words)
            count += words.count { it in lookingFor }
        }
    }

    println(count)
}