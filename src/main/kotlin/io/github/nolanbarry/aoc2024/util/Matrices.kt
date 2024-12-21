package io.github.nolanbarry.aoc2024.util

operator fun <T> List<List<T>>.get(row: Int, column: Int) = this[row][column]
operator fun <T> List<List<T>>.get(pair: Pair<Int, Int>) = this[pair.first, pair.second]
fun <T> List<List<T>>.getOrNull(row: Int, column: Int): T? = if (inbounds(row, column)) this[row, column] else null
fun <T> List<List<T>>.getOrNull(pair: Pair<Int, Int>): T? = if (inbounds(pair)) this[pair] else null
fun <T> List<List<T>>.toMutableMatrix(): List<MutableList<T>> = map { it.toMutableList() }

operator fun <T> List<MutableList<T>>.set(row: Int, column: Int, value: T) {
    this[row][column] = value
}

operator fun <T> List<MutableList<T>>.set(pair: Pair<Int, Int>, value: T) {
    this[pair.first, pair.second] = value
}

fun List<List<*>>.inbounds(row: Int, column: Int) = row in 0..<this.height && column in 0..<this.width
fun List<List<*>>.inbounds(pair: Pair<Int, Int>) = inbounds(pair.first, pair.second)

fun List<List<*>>.coordinatePairs() = sequence {
    forEachIndexed { y, row -> row.forEachIndexed { x, _ -> yield(y to x) } }
}

val List<List<*>>.width get() = this.getOrNull(0)?.size ?: 0
val List<List<*>>.height get() = this.size

fun <T, M> List<List<T>>.mapMatrix(block: (T) -> M): List<List<M>> {
    return this.map { row -> row.map { block(it) } }
}

fun <T, M> List<List<T>>.mapMatrixIndexed(block: (Int, Int, T) -> M): List<List<M>> {
    return mapIndexed { y, row -> row.mapIndexed { x, value -> block(y, x, value) } }
}

fun <T> matrixOf(rows: Int, columns: Int, fill: (Int, Int) -> T) =
    (0..<rows).map { row -> (0..<columns).map { col -> fill(row, col) } }

fun <T> mutableMatrixOf(rows: Int, columns: Int, fill: (Int, Int) -> T) =
    (0..<rows).map { row -> (0..<columns).map { col -> fill(row, col) }.toMutableList() }

fun List<List<*>>.print() = println(joinToString("\n") { it.joinToString("") })
fun Set<Pair<Int, Int>>.print() {
    val rowOffset = minOf { it.first }
    val columnOffset = minOf { it.second }
    val height = maxOf { it.first } - rowOffset + 1
    val width = maxOf { it.second } - columnOffset + 1
    matrixOf(height, width) { row, col ->
        if ((row to col) + (rowOffset to columnOffset) in this) '■' else '□'
    }.print()
}

typealias CoordinatePair = Pair<Int, Int>
typealias LongCoordinatePair = Pair<Long, Long>