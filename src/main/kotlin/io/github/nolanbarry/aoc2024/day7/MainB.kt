package io.github.nolanbarry.aoc2024.day7

import io.github.nolanbarry.aoc2024.util.Input
import kotlin.Long
import kotlin.collections.any
import kotlin.collections.buildList
import kotlin.collections.filter
import kotlin.collections.fold
import kotlin.collections.listOf
import kotlin.collections.map
import kotlin.collections.reduceIndexed
import kotlin.collections.sumOf
import kotlin.io.println
import kotlin.let
import kotlin.ranges.until
import kotlin.repeat
import kotlin.text.lines
import kotlin.text.map
import kotlin.text.padStart
import kotlin.text.split
import kotlin.text.toInt
import kotlin.text.toLong
import kotlin.text.toString
import kotlin.text.trim
import kotlin.to

fun main() {
    val operators = listOf<(Long, Long) -> Long>(
        { a, b -> a + b },
        { a, b -> a * b },
        { a, b -> "$a$b".toLong() }
    )
    val result = Input.get("day7/input")
        .lines()
        .map { it.split(":") }
        .map { it[0].toLong() to it[1].trim().split(" ").map { it.toLong() } }
        .filter { (result, numbers) ->
            buildList { repeat(numbers.size - 1) { add(operators.size) } }
                .fold(1) { acc, next -> acc * next }
                .let { 0 until it }
                .map { it.toString(operators.size).padStart(numbers.size - 1, '0').map { it.toString().toInt() } }
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