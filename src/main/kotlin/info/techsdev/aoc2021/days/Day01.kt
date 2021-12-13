package info.techsdev.aoc2021.days

import java.nio.file.Files
import java.nio.file.Paths

object Day01 {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = Files.readAllLines(Paths.get("day1.txt")).joinToString(separator = "\n")

        println("Part 1: ${countMeasurements(input)}")
        println("Part 2: ${countMeasurementsSlidingWindow(input)}")
    }

    private fun countMeasurements(input: String): Int {
        val lines = input.lines()
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
        var last = lines.first()
        var increments = 0
        lines.stream()
            .skip(1)
            .forEach {
                if (it > last) {
                    increments++
                }
                last = it
            }
        return increments
    }

    private fun countMeasurementsSlidingWindow(input: String): Int {
        val lines = input.lines()
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
        var last = lines[0] + lines[1] + lines[2]
        var increments = 0
        for (window in 1 until lines.size - 2) {
            if (lines[window] + lines[window + 1] + lines[window + 2] > last)
                increments++
            last = lines[window] + lines[window + 1] + lines[window + 2]
        }
        return increments
    }
}