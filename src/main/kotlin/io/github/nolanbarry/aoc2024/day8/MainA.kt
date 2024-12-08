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
                        for (node2 in nodes.drop(i+1)) {
                            yield((node1 - node2) + node1)
                            yield((node2 - node1) + node2)
                        }
                    }
                }
            }
        }
        .filter { it.inbounds(height, width) }
        .toSet()

    antiNodes.forEach { (y, x) ->
        grid[y][x] = '#'
    }

    println(grid.joinToString("\n") { it.joinToString("") })
    println(antiNodes.size)
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = (first + other.first) to (second + other.second)
operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>) = (first - other.first) to (second - other.second)

fun Pair<Int, Int>.inbounds(height: Int, width: Int) = first in 0 until height && second in 0 until width