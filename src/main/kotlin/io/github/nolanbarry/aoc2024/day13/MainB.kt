package io.github.nolanbarry.aoc2024.day13

import io.github.nolanbarry.aoc2024.util.Input
import org.jetbrains.kotlinx.multik.api.linalg.norm
import org.jetbrains.kotlinx.multik.api.linalg.solve
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarrayOf
import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.operations.map
import org.jetbrains.kotlinx.multik.ndarray.operations.minus
import org.jetbrains.kotlinx.multik.ndarray.operations.plus
import org.jetbrains.kotlinx.multik.ndarray.operations.stack
import org.jetbrains.kotlinx.multik.ndarray.operations.sum
import org.jetbrains.kotlinx.multik.ndarray.operations.times
import kotlin.math.roundToLong

fun main() {
    data class ClawMachine(
        val aButton: D1Array<Long>,
        val bButton: D1Array<Long>,
        val prize: D1Array<Long>,
    )

    fun MatchResult?.getCoordinatePair() =
        this!!.let { mk.ndarrayOf(it.groupValues[1].toLong(), it.groupValues[2].toLong()) }

    val result = Input.get("day13/input")
        .split("\n\n")
        .map { raw ->
            ClawMachine(
                Regex("Button A: X([+][0-9]+), Y([+][0-9]+)").find(raw).getCoordinatePair(),
                Regex("Button B: X([+][0-9]+), Y([+][0-9]+)").find(raw).getCoordinatePair(),
                Regex("Prize: X=([0-9]+), Y=([0-9]+)").find(raw).getCoordinatePair()
                    + mk.ndarrayOf(10000000000000L, 10000000000000L)
            )
        }
        .map { clawMachine ->
            mk.linalg.solve(
                mk.stack(clawMachine.aButton, clawMachine.bButton).transpose(),
                clawMachine.prize
            )
        }
        .filter { potentialSolution ->
            mk.linalg.norm(
                potentialSolution.map { it.roundToLong().toDouble() }
                    .minus(potentialSolution)
                    .reshape(1, 2)
            ) < 0.001
        }
        .map { it.also { println(it) } }
        .sumOf {
            it.map { it.roundToLong() }.times(mk.ndarrayOf(3L, 1L)).sum()
        }
    println(result)
}
