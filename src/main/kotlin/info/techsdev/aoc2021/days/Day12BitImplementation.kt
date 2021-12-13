package info.techsdev.aoc2021.days

import java.nio.file.Files
import java.nio.file.Paths

object Day12BitImplementation {
    data class Node(
        val name: String,
        val mask: Int,
        val isStart: Boolean = name == "start",
        val isEnd: Boolean = name == "end",
        val isLarge: Boolean = name[0].isUpperCase(),
        val neighbors: ArrayList<Node> = ArrayList()
    )

    private fun Node.countPaths(visited: Int = 0): Int {
        var count = 0
        for (neighbor in neighbors) {
            if (neighbor.isEnd)
                count++
            else if (neighbor.isLarge || (visited and neighbor.mask) == 0)
                count += neighbor.countPaths(visited or mask)
        }
        return count
    }

    private fun Node.countPathsPt2(visited: Int = 0, doubled: Boolean = false): Int {
        var count = 0
        for (neighbor in neighbors) {
            when {
                neighbor.isEnd -> count++
                neighbor.isStart -> continue
                neighbor.isLarge -> count += neighbor.countPathsPt2(visited or mask, doubled)
                (visited and neighbor.mask) == 0 -> count += neighbor.countPathsPt2(visited or mask, doubled)
                !doubled -> count += neighbor.countPathsPt2(visited or mask, true)
            }
        }
        return count
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val graph = HashMap<String, Node>()
        var shift = 1

        Files.readAllLines(Paths.get("day12.txt")).map { it.split("-") }.forEach { (left, right) ->
            val leftNode = graph.getOrPut(left) { Node(name = left, 1 shl shift++) }
            val rightNode = graph.getOrPut(right) { Node(name = right, 1 shl shift++) }

            leftNode.neighbors += rightNode
            rightNode.neighbors += leftNode
        }

        val startNode = graph.getValue("start")

        for (i in 0 until 100) startNode.countPaths()
        var start = System.nanoTime()
        println("Part 1: ${startNode.countPaths()} (took ${System.nanoTime() - start} ns)")

        for (i in 0 until 100) startNode.countPathsPt2()
        start = System.nanoTime()
        println("Part 2: ${startNode.countPathsPt2()} (took ${System.nanoTime() - start} ns)")
    }
}