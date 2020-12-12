package aoc

class Day10 : Day {

    override val debug = false

    override fun test() {
        solveForInput("day10/test.txt")
        solveForInput("day10/test2.txt")
    }

    override fun run() {
        solveForInput("day10/input.txt")
    }

    fun solveForInput(path: String) {
        var input = readLns(path).map { it.toInt() }.sorted()
        input =  listOf(0) + input + (input.last() + 3)

        println("Part 1: ${part1(input)}")
        println("Part 2: ${part2(input)}")
    }
}

fun part1(input: List<Int>): Int {
    val changes = mutableMapOf<Int, Int>()

    for (i in 0 until input.size-1) {
        val diff = input[i + 1] - input[i]
        changes[diff] = changes.getOrDefault(diff, 0) + 1
    }

    return changes[1]!! * changes[3]!!
}

fun part2(input: List<Int>): Long {
    cache.clear()
    return count(0, input.subList(1, input.size))
}

val cache = mutableMapOf<Pair<Int, Int>, Long>()

fun count(value: Int, rest: List<Int>): Long {

    if(rest.isEmpty()) {
        return 1
    } else if (cache.containsKey(Pair(value, rest.size))) {
        return cache[Pair(value, rest.size)]!!
    }

    var combinations = 0L
    for (i in 0 until minOf(3, rest.size)) {
        val next = rest[i]
        if ((value + 3) >= next) {
            combinations += count(next, rest.subList(i+1, rest.size))
        }
    }
    cache[Pair(value, rest.size)] = combinations

    return combinations
}

fun main() {
    Day10().test()
    Day10().run()
}
