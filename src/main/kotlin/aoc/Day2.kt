package aoc

class Day2 : Day {

    override val DEBUG = false

    override fun run() {
        val rows = readLns("day2/input.txt")

        var part1 = 0
        var part2 = 0

        rows.forEach { row ->
            val left = row.split(":")[0]

            val range = left.split(" ")[0]
            val char = left.split(" ")[1].toCharArray()[0]

            val min = range.split("-")[0].toInt()
            val max = range.split("-")[1].toInt()

            val pwd = row.split(":")[1]

            // Part 1
            if (pwd.toCharArray().filter { it == char }.size in min..max) {
                part1 += 1;
            }

            // Part 2
            if ((pwd[min] == char).xor(pwd[max] == char)) {
                part2 += 1
            }
        }

        println("Part 1: $part1")
        println("Part 2: $part2")
    }
}

fun main() {
    Day2().run()
}

