package io.github.nolanbarry.aoc2024.day10

import io.github.nolanbarry.aoc2024.util.Input

fun main() {
    val map = Input.get("day10/input-test")
        .lines()
        .map { it.map { it.toString().toInt() } }

    val result = sequence { map.forEachIndexed { y, row -> row.forEachIndexed { x, _ -> yield(y to x) } } }
        .filter { (y, x) -> map[y, x] == 0 }
        .sumOf { (y, x) ->
            fun search(row: Int, col: Int, expect: Int): Set<Pair<Int, Int>> {
                if (!map.inbounds(row, col) || map[row, col] != expect) return emptySet()
                if (map[row, col] == 9) return setOf(row to col)
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
