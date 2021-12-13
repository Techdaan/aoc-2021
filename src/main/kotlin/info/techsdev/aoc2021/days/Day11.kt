package info.techsdev.aoc2021.days

import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.atomic.AtomicInteger

object Day11 {
    private fun Array<IntArray>.stepSingle(x: Int, y: Int, flashes: AtomicInteger) {
        if (x < 0 || y < 0 || y >= size || x >= this[y].size) return

        if (this[y][x]++ == 9) {
            for (dx in -1..1) for (dy in -1..1) stepSingle(dx + x, dy + y, flashes)

            flashes.incrementAndGet()
        }
    }

    private fun Array<IntArray>.step(flashes: AtomicInteger) {
        forEachIndexed { y, row -> row.forEachIndexed { x, _ -> stepSingle(x, y, flashes) } }
        forEachIndexed { y, row -> row.forEachIndexed { x, _ -> if (this[y][x] > 9) this[y][x] = 0 } }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val board = Files.readAllLines(Paths.get("day11.txt"))
            .map { it.toCharArray().map { c -> c.digitToInt() }.toIntArray() }.toTypedArray()

        val flashes = AtomicInteger(0)
        var count = 0
        var i = 0
        while(true) {
            board.step(flashes)
            if (++i == 100) {
                println("Part 1: ${flashes.get()}")
                count++
            }
            if (board.all { n -> n.all { it == 0 } }) {
                println("Part 2: $i")
                count++
            }
            if (count == 2) break
        }

    }
}