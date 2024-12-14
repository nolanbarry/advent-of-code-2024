package io.github.nolanbarry.aoc2024.day13

import io.github.nolanbarry.aoc2024.util.CoordinatePair
import io.github.nolanbarry.aoc2024.util.Input
import io.github.nolanbarry.aoc2024.util.coordinatePairs
import io.github.nolanbarry.aoc2024.util.height
import io.github.nolanbarry.aoc2024.util.mutableMatrixOf
import io.github.nolanbarry.aoc2024.util.plus
import io.github.nolanbarry.aoc2024.util.times

fun main() {
    data class ClawMachine(
        val aButton: CoordinatePair,
        val bButton: CoordinatePair,
        val prize: CoordinatePair
    )

    fun MatchResult?.getCoordinatePair() = this!!.let { it.groupValues[1].toInt() to it.groupValues[2].toInt() }
    val result = Input.get("day13/input")
        .split("\n\n")
        .map { raw ->
            ClawMachine(
                Regex("Button A: X([-+][0-9]+), Y([-+][0-9]+)").find(raw).getCoordinatePair(),
                Regex("Button B: X([-+][0-9]+), Y([-+][0-9]+)").find(raw).getCoordinatePair(),
                Regex("Prize: X=([0-9]+), Y=([0-9]+)").find(raw).getCoordinatePair()
            )
        }
        .mapNotNull { clawMachine ->
            // [↓ A Button] [→ B Button]
            val matrix = mutableMatrixOf(100, 100) { _, _ -> 0 to 0 }
            for (row in 0 until matrix.height) matrix[row][0] = clawMachine.aButton * row
            matrix.coordinatePairs().forEach { (row, col) ->
                if (col == 0) return@forEach
                matrix[row][col] = matrix[row][col - 1] + clawMachine.bButton
            }
            matrix.coordinatePairs()
                .filter { (row, col) -> matrix[row][col] == clawMachine.prize }
                .map { (aButtonPresses, bButtonPresses) -> aButtonPresses * 3 + bButtonPresses }
                .minOrNull()
        }.sum()
    println(result)
}
