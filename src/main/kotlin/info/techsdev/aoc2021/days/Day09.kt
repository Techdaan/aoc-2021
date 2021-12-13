package info.techsdev.aoc2021.days

import java.nio.file.Files
import java.nio.file.Paths

object Day09 {
    data class DangerZone(val x: Int, val y: Int, val height: Int)

    fun Array<IntArray>.findBasins(): List<Int> {
        val copy = Array(this.size) { i -> this[i].copyOf() }
        val sizes = ArrayList<Int>()

        var size = 0
        fun Array<IntArray>.mark(x: Int, y: Int) {
            if (y < 0 || x < 0 || y >= this.size || x >= this[y].size) {
                return
            }

            if (this[y][x] >= 9)
                return

            size++
            this[y][x] = 10

            mark(x - 1, y)
            mark(x + 1, y)
            mark(x, y - 1)
            mark(x, y + 1)
        }

        forEachIndexed { y, row ->
            row.forEachIndexed { x, _ ->
                if (row[x] < 9) {
                    size = 0
                    mark(x, y)
                    sizes += size
                }
            }
        }

        return sizes
    }

    fun findDangerZones(data: Array<IntArray>): List<DangerZone> {
        val zones = ArrayList<DangerZone>()
        data.forEachIndexed { y, row ->
            row.forEachIndexed tile@{ x, char ->
                val score = char

                if (y > 0 && data[y - 1][x] <= score) return@tile
                if (y < data.size - 1 && data[y + 1][x] <= score) return@tile
                if (x > 0 && data[y][x - 1] <= score) return@tile
                if (x < row.size - 1 && data[y][x + 1] <= score) return@tile

                zones += DangerZone(x, y, score)
            }
        }
        return zones
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val rows =
            Files.readAllLines(Paths.get("day9.txt"))
                .map { it.toCharArray().map { c -> c.digitToInt() }.toIntArray() }
                .toTypedArray()
        println("Part 1: ${findDangerZones(rows).sumOf { it.height + 1 }}")
        println("Part 2: ${rows.findBasins().sortedDescending().take(3).reduce { l, r -> l * r}}")
    }
}