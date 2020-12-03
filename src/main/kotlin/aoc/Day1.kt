package aoc

class Day1 : Day {

    override val DEBUG = false

    override fun run() {

        val expenses = readLns("day1/input.txt").map { it.toInt() }
        val sorted = expenses.sorted()

        println("Part 1: ${one(sorted)}")
        println("Part 2: ${two(sorted)}")
    }

    private fun one(sorted: List<Int>): Int {
        with(findSum(sorted, 2020)) {
            return if (this != null)
                this.first * this.second
            else
                -1
        }
    }

    private fun two(sorted: List<Int>): Int {
        for (third in sorted) {
            with(findSum(sorted, 2020 - third)) {
                if (this != null) {
                    return this.first * this.second * third
                }
            }
        }

        return -1
    }

    // Find two numbers in a sorted array which match to 'sum'.
    // Returns the two numbers, or null if no such two numbers are found
    private fun findSum(sorted: List<Int>, sum: Int): Pair<Int, Int>? {
        for (i in sorted.indices) {
            val a = sorted[i]
            if (a > sum)
                break

            for (j in sorted.size-1 downTo i) {
                val b = sorted[j]
                if (a + b == sum) {
                    return Pair(a, b)
                } else if (a + b < sum) {
                    break
                }
            }
        }

        return null
    }
}

fun main() {
    Day1().run()
}