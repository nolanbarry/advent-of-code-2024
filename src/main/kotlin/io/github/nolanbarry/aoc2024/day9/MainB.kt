package io.github.nolanbarry.aoc2024.day9

import io.github.nolanbarry.aoc2024.util.Input

fun main() {
    val blocks = Input.get("day9/input")
        .toList()
        .map { it.toString().toInt() }
        .toMutableList()

    val blockIds = (0..blocks.size).map { null as Int? }.toMutableList()

    var index = blocks.size - 1
    var blockId = (blocks.size + 1) / 2 - 1
    while (index >= 0) {
        if (blockIds[index] == null) {
            val chunkSize = blocks[index]
            val freeSpace = blocks.withIndex()
                .asSequence()
                .filter { (i, _) -> i % 2 == 1 }
                .takeWhile { (i, _) -> i < index }
                .firstOrNull() { (_, size) -> size >= chunkSize }

            if (freeSpace != null) {
                val (index, value) = freeSpace
                blocks[index] = 0
                blocks.add(index + 1, chunkSize)
                blockIds.add(index + 1, blockId)
                blocks.add(index + 2, value - chunkSize)
                blockIds.add(index + 2, null)
            } else {
                blockIds[index] = blockId
                index -= 2
            }
            blockId--
        } else {
            index -= 2
        }
    }

    fun checksum(blockNumber: Int, tapeHead: Int, length: Int): Long {
        return blockNumber * (length.toLong() * (length - 1) / 2 + length.toLong() * tapeHead)
    }

    val startingTapeHead = blocks.runningFold(0) { acc, next -> acc + next }
    val result = blocks
        .withIndex()
        .asSequence()
        .filter { (i, _) -> i % 2 == 0 }
        .filter { (i, _) -> blockIds[i] != null}
        .sumOf { (i, _) -> checksum(blockNumber = blockIds[i]!!, tapeHead = startingTapeHead[i], length = blocks[i])}

    println(result)
}
