package info.techsdev.aoc2021.days

import info.techsdev.aoc2021.days.Day12.countPaths
import java.nio.file.Files
import java.nio.file.Paths

object Day12 {
    data class Node(
        val name: String,
        val isStart: Boolean = name == "start",
        val isEnd: Boolean = name == "end",
        val isLarge: Boolean = name[0].isUpperCase(),
        val neighbors: ArrayList<Node> = ArrayList()
    ) {
        override fun hashCode(): Int = name.hashCode()
        override fun equals(other: Any?): Boolean = other != null && other is Node && other.name == name
    }

    private fun Node.countPaths(visited: HashSet<Node> = HashSet()): Int {
        if (!this.isLarge) visited += this

        var count = 0
        for (neighbor in neighbors) {
            if (neighbor.isEnd)
                count++
            else if (neighbor.isLarge || neighbor !in visited)
                count += neighbor.countPaths(HashSet(visited))
        }

        return count
    }

    private fun Node.countPathsPt2(visited: HashSet<Node> = HashSet(), doubleSmall: Node? = null): Int {
        if (!this.isLarge) visited += this

        var count = 0
        for (neighbor in neighbors) {
            when {
                neighbor.isEnd -> count++
                neighbor.isStart -> continue
                neighbor.isLarge -> count += neighbor.countPathsPt2(HashSet(visited), doubleSmall)
                neighbor !in visited -> count += neighbor.countPathsPt2(HashSet(visited), doubleSmall)
                doubleSmall == null -> count += neighbor.countPathsPt2(HashSet(visited), neighbor)
            }


//            if (neighbor.isEnd)
//                count++
//            else if (neighbor.isStart)
//                continue
//            else if (neighbor.isLarge) {
//                count += neighbor.countPathsPt2(HashSet(visited), doubleSmall)
//            } else {
//                if (neighbor !in visited)
//                    count += neighbor.countPathsPt2(HashSet(visited), doubleSmall)
//                else if (doubleSmall == null) {
//                    count += neighbor.countPathsPt2(HashSet(visited), neighbor)
//                }
//            }
        }

        return count
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val graph = HashMap<String, Node>()
        Files.readAllLines(Paths.get("day12.txt")).map { it.split("-") }.forEach { (left, right) ->
            val leftNode = graph.getOrPut(left) { Node(name = left) }
            val rightNode = graph.getOrPut(right) { Node(name = right) }

            leftNode.neighbors += rightNode
            rightNode.neighbors += leftNode
        }
        println(graph.size)

        run {
            val start = System.currentTimeMillis()
            val startNode = graph.getValue("start")
            println("Part 1: ${startNode.countPaths()} (took ${System.currentTimeMillis() - start}ms)")
        }

        run {
            val start = System.currentTimeMillis()
            val startNode = graph.getValue("start")
            println("Part 2: ${startNode.countPathsPt2()} (took ${System.currentTimeMillis() - start}ms)")
        }

        run {
            val start = System.currentTimeMillis()
            val startNode = graph.getValue("start")
            println("Part 2: ${startNode.countPathsPt2()} (took ${System.currentTimeMillis() - start}ms)")
        }

        run {
            val start = System.currentTimeMillis()
            val startNode = graph.getValue("start")
            println("Part 2: ${startNode.countPathsPt2()} (took ${System.currentTimeMillis() - start}ms)")
        }

        run {
            val start = System.currentTimeMillis()
            val startNode = graph.getValue("start")
            println("Part 2: ${startNode.countPathsPt2()} (took ${System.currentTimeMillis() - start}ms)")
        }

        run {
            val start = System.currentTimeMillis()
            val startNode = graph.getValue("start")
            println("Part 2: ${startNode.countPathsPt2()} (took ${System.currentTimeMillis() - start}ms)")
        }

        run {
            val start = System.currentTimeMillis()
            val startNode = graph.getValue("start")
            println("Part 2: ${startNode.countPathsPt2()} (took ${System.currentTimeMillis() - start}ms)")
        }

        run {
            val start = System.currentTimeMillis()
            val startNode = graph.getValue("start")
            println("Part 2: ${startNode.countPathsPt2()} (took ${System.currentTimeMillis() - start}ms)")
        }

        run {
            val start = System.currentTimeMillis()
            val startNode = graph.getValue("start")
            println("Part 2: ${startNode.countPathsPt2()} (took ${System.currentTimeMillis() - start}ms)")
        }

        run {
            val start = System.currentTimeMillis()
            val startNode = graph.getValue("start")
            for (i in 0 until 100) startNode.countPathsPt2()
            println("Part 2: ${startNode.countPathsPt2()} (took ${System.currentTimeMillis() - start}ms)")
        }

        run {
            val start = System.currentTimeMillis()
            val startNode = graph.getValue("start")
            println("Part 2: ${startNode.countPathsPt2()} (took ${System.currentTimeMillis() - start}ms)")
        }

        run {
            val start = System.currentTimeMillis()
            val startNode = graph.getValue("start")
            println("Part 2: ${startNode.countPathsPt2()} (took ${System.currentTimeMillis() - start}ms)")
        }

        run {
            val start = System.currentTimeMillis()
            val startNode = graph.getValue("start")
            println("Part 2: ${startNode.countPathsPt2()} (took ${System.currentTimeMillis() - start}ms)")
        }

        run {
            val start = System.currentTimeMillis()
            val startNode = graph.getValue("start")
            println("Part 2: ${startNode.countPathsPt2()} (took ${System.currentTimeMillis() - start}ms)")
        }

        run {
            val start = System.currentTimeMillis()
            val startNode = graph.getValue("start")
            println("Part 2: ${startNode.countPathsPt2()} (took ${System.currentTimeMillis() - start}ms)")
        }

        run {
            val start = System.currentTimeMillis()
            val startNode = graph.getValue("start")
            println("Part 2: ${startNode.countPathsPt2()} (took ${System.currentTimeMillis() - start}ms)")
        }
    }
}