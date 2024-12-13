package io.github.nolanbarry.aoc2024.day12

import io.github.nolanbarry.aoc2024.util.Input
import io.github.nolanbarry.aoc2024.util.coordinatePairs
import io.github.nolanbarry.aoc2024.util.get
import io.github.nolanbarry.aoc2024.util.inbounds
import io.github.nolanbarry.aoc2024.util.mapMatrix
import io.github.nolanbarry.aoc2024.util.set
import io.github.nolanbarry.aoc2024.util.toMutableMatrix
import io.github.nolanbarry.aoc2024.util.plus

fun main() {
    val matrix = Input.matrixOfChars("day12/input")
    val visited = matrix.mapMatrix { false }.toMutableMatrix()
    val cardinals = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)

    fun searchRegion(coordinate: Pair<Int, Int>, letter: Char): Pair<Int, Int> {
        // Count out of bounds or different region as a boundary
        if (!matrix.inbounds(coordinate)) return 1 to 0
        if (matrix[coordinate] != letter) return 1 to 0
        // Don't count an already visited coordinate in this same region
        if (visited[coordinate]) return 0 to 0
        visited[coordinate] = true
        var perimeterToArea = 0 to 1
        for (direction in cardinals) {
            perimeterToArea += searchRegion(coordinate + direction, letter)
        }
        return perimeterToArea
    }

    val result = matrix.coordinatePairs()
        .filter { visited[it] == false }
        .map { searchRegion(it, matrix[it]) }
        .sumOf { (perimeter, area) -> perimeter * area }

    println(result)
}
