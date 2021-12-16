package info.techsdev.aoc2021.days

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.HashMap

object Day15 {
    data class Node(
        val level: Int, val x: Int, val y: Int, private val uuid: UUID = UUID.randomUUID(), var cost: Int = level
    ) {
        override fun hashCode(): Int = uuid.hashCode()
        override fun equals(other: Any?): Boolean = other != null && other is Node && other.uuid == uuid
    }

    private fun enqueue(openTiles: PriorityQueue<Node>, remaining: HashMap<Int, Node>, x: Int, y: Int, fromCost: Int) {
        val node = remaining[x shl 16 or y] ?: return
        remaining.remove(x shl 16 or y)
        node.cost += fromCost
        openTiles += node
    }

    private fun HashMap<Int, Node>.findCheapestPath(width: Int, height: Int): Int {
        val openTiles = PriorityQueue<Node> { a, b -> a.cost - b.cost }
        enqueue(openTiles, this, 0, 0, -this[0]!!.cost)
        while (openTiles.isNotEmpty()) {
            val current = openTiles.poll()

            if (current.x == width - 1 && current.y == height - 1) {
                return current.cost
            }

            enqueue(openTiles, this, current.x + 1, current.y, current.cost)
            enqueue(openTiles, this, current.x - 1, current.y, current.cost)
            enqueue(openTiles, this, current.x, current.y + 1, current.cost)
            enqueue(openTiles, this, current.x, current.y - 1, current.cost)
        }
        return -1
    }

    private fun List<List<Node>>.tileMap(numW: Int, numH: Int): HashMap<Int, Node> {
        val result = HashMap<Int, Node>()
        val width = this[0].size
        val height = this.size

        for (x in 0 until width * numW) {
            for (y in 0 until height * numH) {
                val level = this[y % height][x % width].level + x / width + y / width
                result[x shl 16 or y] = Node(if (level > 9) level - 9 else level, x, y)
            }
        }

        return result
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val tiles2d = Files.readAllLines(Paths.get("day15.txt"))
            .mapIndexed { y, line -> line.toCharArray().mapIndexed { x, c -> Node(level = (c - '0'), x, y) } }
        val width = tiles2d[0].size
        val height = tiles2d.size

        println("Part 1: ${tiles2d.tileMap(1, 1).findCheapestPath(width, height)}")
        println("Part 2: ${tiles2d.tileMap(5, 5).findCheapestPath(width * 5, height * 5)}")
    }
}