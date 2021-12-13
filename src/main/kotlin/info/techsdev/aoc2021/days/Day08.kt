package info.techsdev.aoc2021.days

import java.nio.file.Files
import java.nio.file.Paths

object Day08 {
    private fun List<String>.getNumber(n: String): Int =
        indexOf(single { s -> s.length == n.length && s.all { n.contains(it) } })

    @JvmStatic
    fun main(args: Array<String>) {
        val input = Files.readAllLines(Paths.get("day8.txt"))

        println("Part 1: ${input.map { it.split("|")[1] }.map { it.split(" ")
            .map { s -> s.trim() } }.sumBy { it.count { s -> s.length in 2..4 || s.length == 7 } }}")

        println("Part 2: ${input.sumBy { line ->
            val numbers = line.split("|")[0].split(" ").map(String::trim)
            val one = numbers.single { it.length == 2 }
            val four = numbers.single { it.length == 4 }
            val seven = numbers.single { it.length == 3 }
            val eight = numbers.single { it.length == 7 }
            val six = numbers.single { n -> n.length == 6 && !one.all { n.contains(it) } }
            val zero = numbers.single { n -> n.length == 6 && n != six && !four.all { n.contains(it) } }
            val nine = numbers.single { n -> n.length == 6 && n != six && n != zero }
            val three = numbers.single { n -> n.length == 5 && seven.all { n.contains(it) } }
            val five = numbers.single { n -> n.length == 5 && n != three && nine.count { !n.contains(it) } == 1 }
            val two = numbers.single { n -> n.length == 5 && n != three && n != five }
            val mapping = listOf(zero, one, two, three, four, five, six, seven, eight, nine)

            line.split("|")[1].split(" ").filter { it.isNotEmpty() }
                .joinToString(separator = "") { mapping.getNumber(it).toString() }.toInt()
        }}")
    }
}