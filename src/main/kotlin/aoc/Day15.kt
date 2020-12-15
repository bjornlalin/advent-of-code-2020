package aoc

class Day15 : Day {

    override val debug = false

    override fun test() {
        println("Part 1: ${count(arrayOf(0,3,6).toIntArray(), 2020)}")  // expected: 436
        println("Part 2: ${count(arrayOf(0,3,6).toIntArray(), 30_000_000)}") // expected: 175594
    }


    override fun run() {
        println("Part 1: ${count(arrayOf(6,4,12,1,20,0,16).toIntArray(), 2020)}")
        println("Part 2: ${count(arrayOf(6,4,12,1,20,0,16).toIntArray(), 30000000)}")
    }

    fun count(start: IntArray, rounds: Int): Int {

        // How many times has a number been spoken?
        val spokenCount = mutableMapOf<Int, Int>()

        // In which round was the number last spoken?
        val spokenInRound = mutableMapOf<Int, Int>()

        // Keep track of the round
        var round = 1

        // Keep track of the information for the last (previous) number (including the diff which becomes the next number)
        var lastNumberSpoken = -1
        var lastNumberRoundsSinceLastSpoken = 0

        // Bootstrap with starting list
        for (t in start.indices) {
            lastNumberSpoken = start[t]
            spokenCount[lastNumberSpoken] = 1
            spokenInRound[lastNumberSpoken] = round
            round += 1
        }

        // Algorithm
        while (round <= rounds) {

            var spokenInThisRound: Int

            // Was the last value spoken said for the first time?
            val timesSpoken = spokenCount.getOrDefault(lastNumberSpoken, 0)
            if (timesSpoken <= 1) {
                spokenInThisRound = 0
            } else {
                spokenInThisRound = lastNumberRoundsSinceLastSpoken
            }

            // Update how many times we have said this number
            spokenCount[spokenInThisRound] = spokenCount.getOrDefault(spokenInThisRound, 0) + 1

            // Keep track if necessary info about this number to base decision/value for next number on
            lastNumberRoundsSinceLastSpoken = round - spokenInRound.getOrDefault(spokenInThisRound, 0)
            lastNumberSpoken = spokenInThisRound

            // Keep track of when this number was last spoken (in which round)
            spokenInRound[spokenInThisRound] = round

            debug("Speaking $spokenInThisRound in round $round (total ${spokenCount.size})")

            round += 1
        }

        return lastNumberSpoken
    }
}

fun main() {
    Day15().test()
    Day15().run()
}