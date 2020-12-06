package aoc

class Day6 : Day {

    override val debug = false

    override fun run() {
        solve()
        solveClean()
    }

    /* Original implementation */
    fun solve() {
        var rows = readLns("day6/input.txt")

        // Add trailing empty line
        rows = rows.toMutableList()
        rows.add("")

        // Keep track of the sums
        var sumAny = 0
        var sumAll = 0

        // Temporary state for each group
        var n:Long = 0
        var answers = mutableMapOf<Char, Long>()

        rows.forEach { row ->
            if(row.isBlank()) {
                // Collect results (part 1 - at least one person answered yes)
                sumAny += answers.keys.size

                // Collect results (part 2 - all persons answered yes)
                answers.forEach { (_, value) ->
                    if (value == n)
                        sumAll += 1
                }

                // Reset group
                answers = mutableMapOf()
                n = 0

            } else {
                row.toCharArray().forEach { answers[it] = answers.getOrDefault(it, 0) + 1 }
                n += 1
            }
        }

        println("Part 1: $sumAny")
        println("Part 2: $sumAll")
    }

    /* After cleanup (post-submit) */
    fun solveClean() {
        var cnt1 = 0L
        var cnt2 = 0L

        applyToGroupOfLines("day6/input.txt") { rows ->
            val answers = mutableMapOf<Char, Long>()
            rows.forEach { row -> row.toCharArray().forEach { answers[it] = answers.getOrDefault(it, 0) + 1 } }
            cnt1 += answers.keys.size
            cnt2 += answers.values.map { it == rows.size.toLong() }.filter { it }.size
        }

        println("Part 1: $cnt1")
        println("Part 2: $cnt2")
    }
}

fun main() {
    Day6().run()
}
