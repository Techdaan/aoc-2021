package info.techsdev.aoc2021.days

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

object Day14 {
    fun Map<String, Long>.update(updates: Map<String, String>): Map<String, Long> {
        val out = HashMap<String, Long>()
        forEach { (pair, count) ->
            val insert = updates[pair]
            if (insert == null) {
                out[pair] = count
                return@forEach
            }
            out["${pair[0]}$insert"] = count + out.getOrDefault("${pair[0]}$insert", 0L)
            out["$insert${pair[1]}"] = count + out.getOrDefault("$insert${pair[1]}", 0L)
        }
        return out
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val lines = Files.readAllLines(Paths.get("day14.txt"))
        var map = lines.first().windowed(size = 2).groupBy { it }.mapValues { it.value.size.toLong() }
        val initial = lines.first()
        val insertionRules = lines.stream().skip(2).map { it.split(" -> ") }
            .collect(Collectors.toMap({ it[0] }, { it[1] }))

        for (i in 1 until 41) {
            map = map.update(insertionRules)
            if (i == 10 || i == 40) {
                val counts = map.entries.groupBy { it.key[1] }.mapValues { it.value.sumOf { it.value } }.toMutableMap()
                counts[initial[0]] = counts.getOrDefault(initial[0], 0L) + 1L
                val mostCommon = counts.maxByOrNull { it.value }!!.value
                val leastCommon = counts.minByOrNull { it.value }!!.value
                println("After $i iterations: ${mostCommon - leastCommon}")
            }
        }
    }
}