package info.techsdev.aoc2021.days

import java.nio.file.Files
import java.nio.file.Paths

object Day06 {
    fun IntArray.part1(days: Int): Long {
        val population = LongArray(10)
        forEach { i -> population[i]++ }
        for (day in 0 until days) {
            population[9] = population[0]
            System.arraycopy(population, 1, population, 0, 8)
            population[8] = population[9]
            population[6] += population[9]
        }
        return population.sum() - population[9]
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val initial = Files.readAllBytes(Paths.get("day6.txt")).toString(Charsets.UTF_8)
            .split(",").map(String::toInt).toIntArray()
        println("18 days:  ${initial.part1(18)}")
        println("80 days:  ${initial.part1(80)}")
        println("256 days: ${initial.part1(256)}")
    }
}