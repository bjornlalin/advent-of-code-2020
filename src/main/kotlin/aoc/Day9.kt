package aoc

class Day9 : Day {

    override val debug = false

    override fun test() {
        solveForInput("day9/test.txt", 5, 5)
    }

    override fun run() {
        solveForInput("day9/input.txt", 25, 25)
    }

    fun solveForInput(path: String, preambleSize: Int, windowSize: Int) {
        val input = readLns(path).map { it.toLong() }

        // Solve part 1
        val index = findIndexOfFirstNotSum(preambleSize, windowSize, input)

        // Solve part 2
        val range = findRangeThatSumsTo(input[index], input) ?: throw Exception("Could not find range")
        val parts = input.subList(range.first, range.second)
        val sumSmallestAndLargest = parts.maxOrNull()!! + parts.minOrNull()!!

        println("Part 1: ${input[index]}")
        println("Part 2: $sumSmallestAndLargest")
    }

    private fun findIndexOfFirstNotSum(preambleSize: Int, windowSize: Int, input: List<Long>): Int {
        for (i in preambleSize until input.size) {
            val window = input.subList(i - windowSize, i)
            if(!containsPairWhichSumsTo(window, input[i])) {
                return i
            }
        }

        return -1
    }

    private fun findRangeThatSumsTo(desired: Long, input: List<Long>): Pair<Int, Int>? {
        for (i in input.indices) {
            for (j in i+1 until input.size) {
                val sum = input.subList(i, j).sum()
                if (sum == desired)
                    return Pair(i, j)
                if (sum > desired)
                    break
            }
        }

        return null
    }

    private fun containsPairWhichSumsTo(window: List<Long>, sum: Long): Boolean {
        for (i in window.indices) {
            for (j in i until window.size) {
                if (window[i] + window[j] == sum)
                    return true
            }
        }

        return false
    }
}

fun main() {
    Day9().test()
    Day9().run()
}
