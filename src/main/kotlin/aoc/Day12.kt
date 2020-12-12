package aoc

import kotlin.math.absoluteValue

val LEFTS = mapOf('E' to 'N', 'N' to 'W', 'W' to 'S', 'S' to 'E')
val RIGHTS = mapOf('E' to 'S', 'S' to 'W', 'W' to 'N', 'N' to 'E')
val DELTA = mapOf('E' to Pair(1, 0), 'N' to Pair(0, 1), 'W' to Pair(-1, 0), 'S' to Pair(0, -1))

class Day12 : Day {

    override val debug = false

    override fun test() {
        solveForInput("day12/test.txt")
    }

    override fun run() {
        solveForInput("day12/input.txt")
    }

    private fun solveForInput(path: String) {
        val instructions = readLns(path)

        val ship = Ship()
        val ship2 = Ship2()

        instructions.forEach { instr ->
            val dir = instr[0]
            val steps = instr.substring(1).toInt()

            ship.move(dir, steps)
            ship2.move(dir, steps)

            debug("instr: $instr")
            debug("  ship1: ${ship.pos}")
            debug("  ship2: ${ship2.pos} ${ship2.waypoint}")
        }

        println("Part 1: ${ship.pos.first.absoluteValue + ship.pos.second.absoluteValue}")
        println("Part 2: ${ship2.pos.first.absoluteValue + ship2.pos.second.absoluteValue}")
    }
}

fun main() {
    Day12().test()
    Day12().run()
}

class Ship {
    var pos = Pair(0, 0)
    var dir = 'E'

    fun move(dir: Char, value: Int) {
        when (dir) {
            'L', 'R' -> turn(dir, value)
            'F' -> forward(value)
            'N', 'E', 'S', 'W' -> translate(dir, value)
        }
    }

    private fun translate(dir: Char, steps: Int) {
        pos = Pair(pos.first + steps * DELTA[dir]!!.first, pos.second + steps * DELTA[dir]!!.second)
    }

    private fun forward(steps: Int) {
        pos = Pair(pos.first + steps * DELTA[dir]!!.first, pos.second + steps * DELTA[dir]!!.second)
    }

    private fun turn(dir: Char, degrees: Int) {
        repeat(degrees / 90) {
            if (dir == 'L') {
                this.dir = LEFTS[this.dir] ?: error("UNKNOWN DIRECTION")
            }
            if (dir == 'R') {
                this.dir = RIGHTS[this.dir] ?: error("UNKNOWN DIRECTION")
            }
        }
    }
}

class Waypoint(startX: Int, startY: Int) {
    var pos = Pair(startX, startY)

    fun rotate(dir: Char, degrees: Int) {
        val steps = degrees / 90
        when (dir) {
            'L' -> repeat(steps) { pos = Pair(-1 * pos.second, pos.first) }
            'R' -> repeat(steps) { pos = Pair(pos.second, -1 * pos.first) }
        }
    }

    fun translate(dir: Char, steps: Int) {
        pos = Pair(pos.first + steps * DELTA[dir]!!.first, pos.second + steps * DELTA[dir]!!.second)
    }

    override fun toString(): String {
        return "[$pos]"
    }
}

class Ship2 {
    val waypoint = Waypoint(10, 1)
    var pos = Pair(0, 0)

    fun move(dir: Char, value: Int) {
        when (dir) {
            'L', 'R' -> waypoint.rotate(dir, value)
            'F' -> pos = Pair(pos.first + (value * waypoint.pos.first), pos.second + (value * waypoint.pos.second))
            'N', 'E', 'S', 'W' -> waypoint.translate(dir, value)
        }
    }
}