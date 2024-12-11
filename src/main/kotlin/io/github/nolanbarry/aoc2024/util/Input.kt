package io.github.nolanbarry.aoc2024.util

import java.nio.charset.Charset

object Input {
    fun get(filepath: String): String {
        return this.javaClass.classLoader.getResourceAsStream(filepath)?.readBytes()?.toString(Charset.defaultCharset())
            ?: throw RuntimeException("Resource '$filepath' does not exist.")
    }

    fun mutableMatrixOfChars(filepath: String) = get(filepath).lines().map { it.toMutableList() }
    fun matrixOfChars(filepath: String): List<List<Char>> = mutableMatrixOfChars(filepath)

    fun mutableMatrixOfInts(filepath: String) =
        get(filepath).lines().map { it.map { it.toString().toInt() }.toMutableList() }

    fun matrixOfInts(filepath: String): List<List<Int>> = mutableMatrixOfInts(filepath)
}