package io.github.nolanbarry.aoc2024.day6

import io.github.nolanbarry.aoc2024.util.Input
import io.github.nolanbarry.aoc2024.util.coordinatePairs
import io.github.nolanbarry.aoc2024.util.get
import io.github.nolanbarry.aoc2024.util.inbounds
import io.github.nolanbarry.aoc2024.util.plus
import io.github.nolanbarry.aoc2024.util.rotate90
import io.github.nolanbarry.aoc2024.util.set

fun main() {
    val matrix = Input.mutableMatrixOfChars("day6/input")

    var position = matrix.coordinatePairs().first { (y, x) -> matrix[y][x] == '^' }
    var direction = -1 to 0

    val potentialObstructions = mutableSetOf<Pair<Int, Int>>()
    val obstructionConsidered = mutableSetOf<Pair<Int, Int>>()

    fun step(
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
        map: List<List<Char>>
    ): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        return if (!matrix.inbounds(position + direction)) return (position + direction) to direction
        else if (map[position + direction] == '#') position to direction.rotate90()
        else (position + direction) to direction
    }

    fun obstructionCausesLoop(obstruction: Pair<Int, Int>): Boolean {
        if (matrix[obstruction] == '#') return false
        val visited = mutableMapOf<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>()
        var position = position
        var direction = direction
        val map = matrix.map { it.toMutableList() }.also { it[obstruction] = '#' }
        while (matrix.inbounds(position) && direction !in (visited[position] ?: emptySet())) {
            visited.computeIfAbsent(position) { mutableSetOf() }.add(direction)
            val (p, d) = step(position, direction, map)
            position = p; direction = d
        }
        return matrix.inbounds(position)
    }

    while (matrix.inbounds(position + direction)) {
        if (position + direction !in obstructionConsidered) {
            if (obstructionCausesLoop(position + direction)) {
                potentialObstructions.add(position + direction)
                matrix[position + direction] = 'O'
            }
            obstructionConsidered.add(position + direction)
        }

        val (p, d) = step(position, direction, matrix)
        position = p; direction = d
    }

    println(matrix.joinToString("\n") { it.joinToString("") })
    println(potentialObstructions.size)
}