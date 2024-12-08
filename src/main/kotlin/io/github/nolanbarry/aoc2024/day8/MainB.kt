package io.github.nolanbarry.aoc2024.day8

import io.github.nolanbarry.aoc2024.util.Input

fun main() {
    val grid = Input.get("day8/input")
        .lines()
        .map { it.trim().toCharArray().toMutableList() }

    val (height, width) = grid.size to grid.first().size
    val antiNodes = sequence { for (y in 0 until height) for (x in 0 until width) yield(y to x) }
        .filter { (y, x) -> grid[y][x] != '.' }
        .groupBy { (y, x) -> grid[y][x] }
        .let { groups ->
            sequence {
                groups.values.forEach { nodes ->
                    for ((i, node1) in nodes.withIndex()) {
                        for (node2 in nodes.drop(i + 1)) {
                            val difference = node1 - node2
                            generateSequence(node1) { it + difference }
                                .takeWhile { it.inbounds(height, width) }
                                .let { yieldAll(it) }

                            generateSequence(node2) { it - difference }
                                .takeWhile { it.inbounds(height, width) }
                                .let { yieldAll(it) }
                        }
                    }
                }
            }
        }
        .toSet()

    antiNodes.forEach { (y, x) ->
        grid[y][x] = '#'
    }

    println(grid.joinToString("\n") { it.joinToString("") })
    println(antiNodes.size)
}

fun Int.gcd(that: Int): Int {
    var (a, b) = this to that
    while (b != 0) {
        val temp = b
        b = a % b
        a = temp
    }
    return a
}

fun Pair<Int, Int>.compact() = first.gcd(second).let { first / it to second / it }
