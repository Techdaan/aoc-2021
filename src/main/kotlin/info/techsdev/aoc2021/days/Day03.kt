package info.techsdev.aoc2021.days

import java.nio.file.Files
import java.nio.file.Paths

object Day03 {
    private fun List<String>.filter(index: Int = 0, oxygen: Boolean): List<String> {
        val numOnes = count { it[index] == '1' }
        val numZeroes = size - numOnes

        val predicate = if (oxygen) {
            if (numOnes >= numZeroes) '1' else '0'
        } else {
            if (numOnes >= numZeroes) '0' else '1'
        }

        val filtered = filter { it[index] == predicate }

        if (filtered.size > 1)
            return filtered.filter(index + 1, oxygen)
        return filtered
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = Files.readAllLines(Paths.get("day3.txt"))

        val counts = IntArray(input[0].length)
        input.forEach {
            it.forEachIndexed { i, c ->
                counts[i] += if (c == '1') 1 else -1
            }
        }

        val gamma = counts.map { if (it <= 0) '0' else '1' }.joinToString(separator = "")
        val epsilon = counts.map { if (it <= 0) '1' else '0' }.joinToString(separator = "")
        println("Power consumption = ${gamma.toInt(2) * epsilon.toInt(2)}")

        val oxygenGeneratorRating = input.filter(oxygen = true).first()
        val co2ScrubberRating = input.filter(oxygen = false).first()

        println("Oxygen ${oxygenGeneratorRating.toInt(2)}")
        println("CO2 ${co2ScrubberRating.toInt(2)}")
        println("Life support rating = ${oxygenGeneratorRating.toInt(2) * co2ScrubberRating.toInt(2)}")
    }
}