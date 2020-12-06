package aoc

class Day3 : Day {

    override val debug = true

    override fun run() {
        val rows = readLns("day3/input.txt")

        // The slopes we want to look at
        val slopes = listOf(
                Pair(1, 1),
                Pair(1, 3),
                Pair(1, 5),
                Pair(1, 7),
                Pair(2, 1))

        // Collect the results here
        val collisions = mutableMapOf<Pair<Int, Int>, Long>()

        // Solve for each slope and collect results
        for (slope in slopes) {
            collisions[slope] = countCollisions(rows, slope)
            debug("Slope $slope = ${collisions[slope]}")
        }

        // Reduce all results to the product
        val product = collisions.values.reduce(fun(sum, nCollisions): Long = sum * nCollisions)

        println("Part 1: ${collisions[Pair(1, 3)]}")
        println("Part 2: $product")
    }
}

fun countCollisions(rows: List<String>, slope: Pair<Int, Int>): Long {
    var nCollisions = 0
    var row = 0
    var col = 0

    while (row < rows.size) {
        if (rows[row].toCharArray()[col] == '#') {
            nCollisions += 1
        }

        // Take a step
        row += slope.first
        col += slope.second

        // Make sure we don't overflow. First attempt I expanded the strings, but mod (%) is the good way
        col %= rows.first().length
    }

    return nCollisions.toLong()
}

fun main() {
    Day3().run()
}

