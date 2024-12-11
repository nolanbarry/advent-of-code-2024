package io.github.nolanbarry.aoc2024.day10

import io.github.nolanbarry.aoc2024.util.Input

fun main() {
    val map = Input.get("day10/input")
        .lines()
        .map { it.map { it.toString().toInt() } }

    val result = sequence { map.forEachIndexed { y, row -> row.forEachIndexed { x, _ -> yield(y to x) } } }
        .filter { (y, x) -> map[y, x] == 0 }
        .sumOf { (y, x) ->
            fun search(row: Int, col: Int, expect: Int): Int {
                if (!map.inbounds(row, col) || map[row, col] != expect) return 0
                if (map[row, col] == 9) return 1
                return listOf(
                    search(row, col + 1, expect + 1),
                    search(row, col - 1, expect + 1),
                    search(row + 1, col, expect + 1),
                    search(row - 1, col, expect + 1),
                ).sum()
            }
            search(y, x, 0)
        }

    println(result)
}

operator fun List<List<Int>>.get(row: Int, column: Int) = this[row][column]
fun List<List<*>>.inbounds(row: Int, column: Int) = row in 0..<this.size && column in 0..<this[0].size