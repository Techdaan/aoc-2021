package info.techsdev.aoc2021.days

import java.nio.file.Files
import java.nio.file.Paths

object Day02 {
    @JvmStatic
    fun main(args: Array<String>) {
        var horizontal = 0
        var depth = 0
        Files.readAllLines(Paths.get("day2.txt"))
            .map { it.split(" ") }
            .forEach { when(it[0]) {
                "forward" -> horizontal += it[1].toInt()
                "down" -> depth += it[1].toInt()
                "up" -> depth -= it[1].toInt()
            } }
        println("Part 1: ${horizontal * depth}")

        horizontal = 0
        depth = 0
        var aim = 0
        Files.readAllLines(Paths.get("day2.txt"))
            .map { it.split(" ") }
            .forEach { when(it[0]) {
                "forward" -> {
                    horizontal += it[1].toInt()
                    depth += aim * it[1].toInt()
                }
                "down" -> aim += it[1].toInt()
                "up" -> aim -= it[1].toInt()
            } }
        println("Part 2: ${horizontal * depth}")
    }
}