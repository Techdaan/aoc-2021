package info.techsdev.aoc2021.days

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.abs

object Day07 {
    @JvmStatic
    fun main(args: Array<String>) {
        val values = Files.readAllBytes(Paths.get("day7.txt")).toString(Charsets.UTF_8).split(",")
            .filter { it.isNotEmpty() }
            .map { it.trim().toInt() }
            .toIntArray()
        val min = (values.minOrNull()!! until values.maxOrNull()!!).minByOrNull { target -> values.sumBy { abs(target - it) } }!!
        println("Target dist: $min, fuel required: ${values.sumOf { abs(min - it) }}")
        val avg = values.average().toInt() // might need ceil, not sure /shrug
        println("Target dist: $avg, fuel required: ${values.map { abs(avg - it) }.sumOf { (it * it + it) / 2 }}")
    }
}