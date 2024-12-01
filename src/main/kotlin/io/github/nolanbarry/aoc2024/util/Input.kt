package io.github.nolanbarry.aoc2024.util

import java.nio.charset.Charset

object Input {
    fun get(filepath: String): String {
        return this.javaClass.classLoader.getResourceAsStream(filepath)?.readBytes()?.toString(Charset.defaultCharset())
            ?: throw RuntimeException("Resource '$filepath' does not exist.")
    }
}