package io.github.nolanbarry.aoc2024.day8

import io.github.nolanbarry.aoc2024.util.Input
import io.github.nolanbarry.aoc2024.util.coordinatePairs
import io.github.nolanbarry.aoc2024.util.inbounds
import io.github.nolanbarry.aoc2024.util.minus
import io.github.nolanbarry.aoc2024.util.plus

fun main() {
    val matrix = Input.mutableMatrixOfChars("day8/input")

    val antiNodes = matrix.coordinatePairs()
        .filter { (y, x) -> matrix[y][x] != '.' }
        .groupBy { (y, x) -> matrix[y][x] }
        .let { groups ->
            sequence {
                groups.values.forEach { nodes ->
                    for ((i, node1) in nodes.withIndex()) {
                        for (node2 in nodes.drop(i + 1)) {
                            val difference = (node1 - node2).compact()
                            generateSequence(node1) { it + difference }
                                .takeWhile { matrix.inbounds(it) }
                                .let { yieldAll(it) }

                            generateSequence(node2) { it - difference }
                                .takeWhile { matrix.inbounds(it) }
                                .let { yieldAll(it) }
                        }
                    }
                }
            }
        }
        .toSet()

    antiNodes.forEach { (y, x) ->
        matrix[y][x] = '#'
    }

    println(matrix.joinToString("\n") { it.joinToString("") })
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
