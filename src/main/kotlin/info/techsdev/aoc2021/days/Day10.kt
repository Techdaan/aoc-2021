package info.techsdev.aoc2021.days

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

object Day10 {
    private val ERROR_SCORES = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    private val AUTOCOMPLETE_SCORES = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

    enum class Braces(val l: Char, val r: Char) {
        ROUND('(', ')'), SQUARE('[', ']'), CURLY('{', '}'), HTML('<', '>'), ;
    }

    data class BraceSequence(val corrupted: Char?, val completionScore: Long?)

    private fun String.toBraceSequence(): BraceSequence {
        val stack = Stack<Char>()
        forEach { c ->
            val pair = Braces.values().single { it.l == c || it.r == c }
            if (c == pair.l) stack.push(pair.r)
            else if (stack.peek() != pair.r) return BraceSequence(c, null)
            else stack.pop()
        }
        var score = 0L
        while (stack.isNotEmpty()) score = score * 5 + AUTOCOMPLETE_SCORES.getValue(stack.pop())
        return BraceSequence(null, score)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val sequences = Files.readAllLines(Paths.get("day10.txt")).map { it.toBraceSequence() }
        println("Part 1: ${sequences.mapNotNull { it.corrupted }.sumOf { ERROR_SCORES.getValue(it) }}")
        println("Part 2: ${sequences.mapNotNull { it.completionScore }.sorted().let { it[it.size / 2] }}")
    }
}