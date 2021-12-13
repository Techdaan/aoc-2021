package info.techsdev.aoc2021.days

import java.nio.file.Files
import java.nio.file.Paths

object Day05 {
    private fun Int.rangeToZero(): IntProgression =
        IntProgression.fromClosedRange(this, 0, if (this >= 0) -1 else 1)

    data class Point(val x: Int, val y: Int) {
        companion object {
            fun from(s: String): Point = s.split(",").let { Point(it[0].toInt(), it[1].toInt()) }
        }
    }

    data class Line(val from: Point, val to: Point) {
        private fun MutableMap<Int, Int>.inc(x: Int, y: Int) {
            this[x * 10000 + y] = getOrDefault(x * 10000 + y, 0) + 1
        }

        fun draw(map: HashMap<Int, Int>, diagonal: Boolean = false) {
            val dx = to.x - from.x
            val dy = to.y - from.y

            if (diagonal && dx != 0 && dy != 0) {
                val y = dy.rangeToZero().iterator()
                for (x in dx.rangeToZero()) map.inc(from.x + x, from.y + y.next())
            } else if (!diagonal && dx == 0) for (y in dy.rangeToZero()) map.inc(from.x, from.y + y)
            else if (!diagonal && dy == 0) for (x in dx.rangeToZero()) map.inc(from.x + x, from.y)
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val lines = Files.readAllLines(Paths.get("day5.txt"))
            .map { it.split(" -> ") }
            .map { Line(Point.from(it[0]), Point.from(it[1])) }

        val map = HashMap<Int, Int>()
        lines.forEach { it.draw(map) }
        println("Part 1 = ${map.count { it.value > 1 }}")

        lines.forEach { it.draw(map, true) }
        println("Part 1 = ${map.count { it.value > 1 }}")
    }
}