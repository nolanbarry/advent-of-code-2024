package io.github.nolanbarry.aoc2024.day9

import io.github.nolanbarry.aoc2024.util.Input

fun main() {
    val input = Input.get("day9/input")
        .toList()
        .map { it.toString().toLong() }
        .let { ArrayDeque(it) }

    var checksum = 0L
    var tapeHead = 0L
    var fromLeft = 0L
    var fromRight = ((input.size + 1) / 2 - 1).toLong()
    var takingFromLeft = true

    /**
     * Compute sum of multiple consecutive blocks of the same number
     * y = starting index; x = block number; n = block length
     *
     * (0+y)x + (y+1)x + (y+2)x + (y+3)x + (y+4)x + ... + (y+n)x
     * x * ((0+y) + (1+y) + (2+y) + (3+y) + (4+y) + ... + (n+y))
     * x * (n*(n-1)/2 + n*y)
     */
    fun incrementChecksum(blockNumber: Long, length: Long) {
        checksum += blockNumber * (length * (length - 1) / 2 + length * tapeHead)
        tapeHead += length
    }

    while (input.isNotEmpty()) {
        if (takingFromLeft) {
            incrementChecksum(blockNumber = fromLeft++, length = input.removeFirst())
            takingFromLeft = false
        } else {
            val amountToRemoveFromEnd = input.last().coerceAtMost(input.first())
            incrementChecksum(blockNumber = fromRight, length = amountToRemoveFromEnd)
            input[input.size - 1] -= amountToRemoveFromEnd
            input[0] -= amountToRemoveFromEnd
            if (input.first() == 0L) {
                input.removeFirst()
                takingFromLeft = true
            }
            if (input.last() == 0L) {
                input.removeLast();
                if (input.size > 0L) input.removeLast();
                fromRight--;
            }
        }
    }

    println(checksum)
}