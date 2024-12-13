package io.github.nolanbarry.aoc2024.util

import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = (first + other.first) to (second + other.second)
operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>) = (first - other.first) to (second - other.second)
fun Pair<Int, Int>.rotate90(): Pair<Int, Int> {
    val newDirectionRadians = (atan2(first.toDouble(), second.toDouble()) + PI / 2)
    return sin(newDirectionRadians).roundToInt() to cos(newDirectionRadians).roundToInt()
}

fun Pair<Int, Int>.rotateNeg90(): Pair<Int, Int> = rotate90().rotate90().rotate90()