package info.techsdev.aoc2021.days

import java.nio.file.Files
import java.nio.file.Paths

object Day04 {
    class BingoCard(val sets: List<HashSet<Int>>, val remaining: HashSet<Int>) {
        fun mark(number: Int): Boolean {
            sets.forEach { if (it.remove(number)) remaining -= number }
            return sets.any { it.isEmpty() }
        }
    }

    private fun Iterator<String>.readBingoCard(): BingoCard? {
        if (!hasNext()) return null
        next() // Blank line

        val r1 = next().split(" ").filter { it.isNotBlank() }.map(String::toInt).toIntArray()
        val r2 = next().split(" ").filter { it.isNotBlank() }.map(String::toInt).toIntArray()
        val r3 = next().split(" ").filter { it.isNotBlank() }.map(String::toInt).toIntArray()
        val r4 = next().split(" ").filter { it.isNotBlank() }.map(String::toInt).toIntArray()
        val r5 = next().split(" ").filter { it.isNotBlank() }.map(String::toInt).toIntArray()

        val c1 = intArrayOf(r1[0], r2[0], r3[0], r4[0], r5[0])
        val c2 = intArrayOf(r1[1], r2[1], r3[1], r4[1], r5[1])
        val c3 = intArrayOf(r1[2], r2[2], r3[2], r4[2], r5[2])
        val c4 = intArrayOf(r1[3], r2[3], r3[3], r4[3], r5[3])
        val c5 = intArrayOf(r1[4], r2[4], r3[4], r4[4], r5[4])

        return BingoCard(
            arrayOf(r1, r2, r3, r4, r5, c1, c2, c3, c4, c5).map(IntArray::toHashSet),
            (r1 + r2 + r3 + r4 + r5).toHashSet()
        )
    }

    private fun List<String>.readCards(): ArrayList<BingoCard> {
        val it = iterator()
        it.next()

        val cards = ArrayList<BingoCard>()
        while (true) {
            cards += (it.readBingoCard() ?: break)
        }

        return cards
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val lines = Files.readAllLines(Paths.get("day4.txt"))

        val numbers = lines[0].split(",").map(String::toInt)

        var cards = lines.readCards()
        for (number in numbers) {
            val completed = cards.firstOrNull { it.mark(number) } ?: continue

            println("Part 1 = ${completed.remaining.sum() * number}")
            break
        }

        cards = lines.readCards()
        for (number in numbers) {
            val last = cards.firstOrNull()
            cards.removeIf { it.mark(number) }
            if (cards.isEmpty()) {
                println("Part 2 = ${last!!.remaining.sum() * number}")
                break
            }
        }
    }
}