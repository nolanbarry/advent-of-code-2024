package io.github.nolanbarry.aoc2024.day12

import io.github.nolanbarry.aoc2024.util.CoordinatePair
import io.github.nolanbarry.aoc2024.util.Input
import io.github.nolanbarry.aoc2024.util.coordinatePairs
import io.github.nolanbarry.aoc2024.util.get
import io.github.nolanbarry.aoc2024.util.inbounds
import io.github.nolanbarry.aoc2024.util.mapMatrix
import io.github.nolanbarry.aoc2024.util.plus
import io.github.nolanbarry.aoc2024.util.print
import io.github.nolanbarry.aoc2024.util.set
import io.github.nolanbarry.aoc2024.util.toMutableMatrix

fun main() {
    val matrix = Input.matrixOfChars("day12/input")
    val visited = matrix.mapMatrix { false }.toMutableMatrix()
    val cardinals = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)

    // If you mark every boundary plot, they'll create as many distinct regions as there are sides of the plot.
    //     X         X
    //   X O X X X X O X
    // X O O O O O O O X
    //   X O X X X X O X
    //     X         X
    fun searchRegion(coordinate: CoordinatePair): Pair<Int, Map<CoordinatePair, Set<CoordinatePair>>> {
        val letter = matrix[coordinate]
        visited[coordinate] = true
        var area = 1
        val boundaryCells = cardinals.associateWith { mutableSetOf<CoordinatePair>() }
        for (direction in cardinals) {
            val neighbor = coordinate + direction
            val neighborInBounds = matrix.inbounds(neighbor)
            if (neighborInBounds && matrix[neighbor] == letter) {
                if (visited[neighbor]) continue
                val (itsArea, itsBoundaryCells) = searchRegion(neighbor)
                area += itsArea
                for ((key, value) in itsBoundaryCells) {
                    boundaryCells[key]!!.addAll(value)
                }
            } else {
                boundaryCells[direction]!!.add(neighbor)
            }
        }
        return area to boundaryCells
    }

    fun searchBoundaryCells(cells: Set<CoordinatePair>): Int {
        val cells = cells.toMutableSet()
        fun search(coordinate: CoordinatePair): Int {
            cells.remove(coordinate)
            cardinals.forEach { direction ->
                val neighbor = coordinate + direction
                if (neighbor in cells) search(neighbor)
            }
            return 1
        }
        return cells.toList().asSequence()
            .filter { it in cells }
            .sumOf { search(it) }
    }

    val result = matrix.coordinatePairs()
        .filter { !visited[it] }
        .map { coordinate -> searchRegion(coordinate) }
        .sumOf { (area, boundaryCells) ->
            if (boundaryCells.values.isNotEmpty()) {
                boundaryCells.values.reduce { a, b -> a + b }.print()
                println()
            }
            area * boundaryCells.values.sumOf { cells -> searchBoundaryCells(cells) }
        }


    println(result)
}
