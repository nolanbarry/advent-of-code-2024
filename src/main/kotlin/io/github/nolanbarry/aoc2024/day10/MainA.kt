package io.github.nolanbarry.aoc2024.day10

import io.github.nolanbarry.aoc2024.util.Input
import io.github.nolanbarry.aoc2024.util.coordinatePairs
import io.github.nolanbarry.aoc2024.util.get
import io.github.nolanbarry.aoc2024.util.inbounds

fun main() {
    val matrix = Input.matrixOfInts("day10/input")

    val result = matrix.coordinatePairs()
        .filter { (y, x) -> matrix[y, x] == 0 }
        .sumOf { (y, x) ->
            fun search(row: Int, col: Int, expect: Int): Set<Pair<Int, Int>> {
                if (!matrix.inbounds(row, col) || matrix[row, col] != expect) return emptySet()
                if (matrix[row, col] == 9) return setOf(row to col)
                return listOf(
                    search(row, col + 1, expect + 1),
                    search(row, col - 1, expect + 1),
                    search(row + 1, col, expect + 1),
                    search(row - 1, col, expect + 1),
                ).reduce { acc, next -> acc + next }
            }
            search(y, x, 0).size
        }

    println(result)
}
