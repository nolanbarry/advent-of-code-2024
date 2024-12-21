package io.github.nolanbarry.aoc2024.util

import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = (first + other.first) to (second + other.second)
operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>) = (first - other.first) to (second - other.second)
operator fun Pair<Int, Int>.rem(other: Pair<Int, Int>) = (first % other.first) to (second % other.second)
operator fun Pair<Int, Int>.times(other: Int) = (first * other) to (second * other)
operator fun Pair<Int, Int>.div(other: Pair<Int, Int>) = (first / other.first) to (second / other.second)

@JvmName("plusLong")
operator fun Pair<Long, Long>.plus(other: Pair<Long, Long>) = (first + other.first) to (second + other.second)
@JvmName("minusLong")
operator fun Pair<Long, Long>.minus(other: Pair<Long, Long>) = (first - other.first) to (second - other.second)
@JvmName("remLong")
operator fun Pair<Long, Long>.rem(other: Pair<Long, Long>) = (first % other.first) to (second % other.second)
@JvmName("timesLong")
operator fun Pair<Long, Long>.times(other: Long) = (first * other) to (second * other)
@JvmName("divLong")
operator fun Pair<Long, Long>.div(other: Pair<Long, Long>) = (first / other.first) to (second / other.second)

val Pair<Long, Long>.magnitudeSquared get() = first*first + second*second

fun Pair<Int, Int>.rotate90(): Pair<Int, Int> {
    val newDirectionRadians = (atan2(first.toDouble(), second.toDouble()) + PI / 2)
    return sin(newDirectionRadians).roundToInt() to cos(newDirectionRadians).roundToInt()
}