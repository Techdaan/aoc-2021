package info.techsdev.aoc2021.days

import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

object Day16 {
    sealed class Packet(val version: Int, val children: List<Packet>) {
        class SumPacket(version: Int, children: List<Packet>) : Packet(version, children) {
            override fun evaluate(): Long = children.fold(0L) { acc, packet -> acc + packet.evaluate() }
        }

        class ProductPacket(version: Int, children: List<Packet>) : Packet(version, children) {
            override fun evaluate(): Long = children.fold(1L) { acc, packet -> acc * packet.evaluate() }
        }

        class MinPacket(version: Int, children: List<Packet>) : Packet(version, children) {
            override fun evaluate(): Long =
                children.minByOrNull(Packet::evaluate)?.evaluate() ?: error("failed to get min")
        }

        class MaxPacket(version: Int, children: List<Packet>) : Packet(version, children) {
            override fun evaluate(): Long =
                children.maxByOrNull(Packet::evaluate)?.evaluate() ?: error("failed to get max")
        }

        class GreaterThanPacket(version: Int, children: List<Packet>): Packet(version, children) {
            override fun evaluate(): Long = if (children[0].evaluate() > children[1].evaluate()) 1L else 0L
        }

        class LessThanPacket(version: Int, children: List<Packet>): Packet(version, children) {
            override fun evaluate(): Long = if (children[0].evaluate() < children[1].evaluate()) 1L else 0L
        }

        class EqualToPacket(version: Int, children: List<Packet>): Packet(version, children) {
            override fun evaluate(): Long = if (children[0].evaluate() == children[1].evaluate()) 1L else 0L
        }

        class Literal(version: Int, val value: Long) : Packet(version, emptyList()) {
            override fun evaluate(): Long = value
        }

        fun sumVersions(): Int = version + children.sumBy(Packet::sumVersions)

        abstract fun evaluate(): Long
    }

    class StringBitStream(val string: String, var offset: Int = 0) {
        private fun readInt(bits: Int): Int {
            offset += bits
            return string.substring(offset - bits, offset).toInt(2)
        }

        private fun readVarLong(): Long {
            var total = 0L
            while (true) {
                val hasMore = readInt(1)
                total = (total shl 4) or readInt(4).toLong()
                if (hasMore == 0) break
            }
            return total
        }

        fun readPacket(): Packet {
            val (version, type) = readInt(3) to readInt(3)

            if (type == 4) return Packet.Literal(version, readVarLong())

            val lenType = readInt(1)
            val children = ArrayList<Packet>()
            if (lenType == 0) {
                val targetLen = readInt(15) + offset
                while (offset < targetLen) {
                    children += readPacket()
                }
            } else {
                for (i in 0 until readInt(11)) {
                    children += readPacket()
                }
            }

            return when (type) {
                0 -> Packet.SumPacket(version, children)
                1 -> Packet.ProductPacket(version, children)
                2 -> Packet.MinPacket(version, children)
                3 -> Packet.MaxPacket(version, children)
                5 -> Packet.GreaterThanPacket(version, children)
                6 -> Packet.LessThanPacket(version, children)
                7 -> Packet.EqualToPacket(version, children)
                else -> error("unknown type: $type")
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val it = Files.readAllBytes(Paths.get("day16.txt")).toString(Charset.defaultCharset())
            .map { c -> Integer.parseInt(c.toString(), 16).toString(2).padStart(4, '0') }
            .joinToString(separator = "")

        val outermost = StringBitStream(it).readPacket()
        println("Version: ${outermost.sumVersions()}")
        println("Evaluated: ${outermost.evaluate()}")
    }
}
