package io.github.nolanbarry.aoc2024.day7

import io.github.nolanbarry.aoc2024.util.Input

fun main() {
    val operators = listOf<(Long, Long) -> Long>(
        { a, b -> a + b },
        { a, b -> a * b }
    )
    val result = Input.get("day7/input")
        .lines()
        .map { it.split(":") }
        .map { it[0].toLong() to it[1].trim().split(" ").map { it.toLong() } }
        .filter { (result, numbers) ->
            buildList { repeat(numbers.size - 1) { add(operators.size) } }
                .fold(1) { acc, next -> acc * next }
                .let { 0 until it }
                .map { it.toString(2).padStart(numbers.size - 1, '0').map { it.toString().toInt() } }
                .map { whichOperator ->
                    numbers.reduceIndexed { i, acc, next ->
                        operators[whichOperator[i - 1]](acc, next)
                    }
                }
                .any { it == result }
        }
        .sumOf { (result, _) -> result }

    println(result)
}