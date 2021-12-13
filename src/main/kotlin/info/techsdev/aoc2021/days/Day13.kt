package info.techsdev.aoc2021.days

import java.nio.file.Files
import java.nio.file.Paths

object Day13 {
    data class Point(val x: Int, val y: Int)

    private fun Set<Point>.foldX(x: Int): Set<Point> =
        map { p -> if (p.x > x) p.copy(x = x - (p.x - x)) else p }.toSet()

    private fun Set<Point>.foldY(y: Int): Set<Point> =
        map { p -> if (p.y > y) p.copy(y = y - (p.y - y)) else p }.toSet()

    @JvmStatic
    fun main(args: Array<String>) {
        val lines = Files.readAllLines(Paths.get("day13.txt"))

        var points =
            lines.map { it.split(",") }.filter { it.size > 1 }.map { (x, y) -> Point(x.toInt(), y.toInt()) }.toSet()

        lines.filter { it.startsWith("f") }.map { it.split(" ")[2].split("=") }.forEachIndexed { i, (dir, num) ->
            when (dir) {
                "x" -> points = points.foldX(num.toInt())
                "y" -> points = points.foldY(num.toInt())
            }
            println("Points remaining at step $i: ${points.size}")
        }

        println()

        val maxX = points.map { it.x }.maxOrNull() ?: throw NullPointerException("No points remaining")
        val maxY = points.map { it.y }.maxOrNull() ?: throw NullPointerException("No points remaining")

        val buffer = Array(maxY + 1) { y ->
            val bldr = StringBuilder()
            for (i in 0..maxX) bldr.append(if (points.any { it.x == i && it.y == y }) '#' else ' ')
            bldr.toString()
        }

        buffer.forEach { println(it) }
    }
}