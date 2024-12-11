package io.github.nolanbarry.aoc2024.day11

import io.github.nolanbarry.aoc2024.util.Input

fun main() {
    val numbers = Input.get("day11/input").split(" ").map { it.toLong() }

    val memo = mutableMapOf<Pair<Long, Int>, Long>()
    fun countStones(number: Long, remainingIterations: Int): Long {
        if (remainingIterations == 0) return 1
        memo[number to remainingIterations]?.let { return it }
        val numberString = number.toString()
        val stones = when {
            number == 0L -> countStones(1, remainingIterations - 1)
            numberString.length % 2 == 0 -> numberString
                .chunked(numberString.length / 2)
                .sumOf { countStones(it.toLong(), remainingIterations - 1) }
            else -> countStones(number * 2024, remainingIterations - 1)
        }
        memo[number to remainingIterations] = stones
        return stones
    }

    val result = numbers.sumOf { countStones(it, 25) }
    println(result)
}
