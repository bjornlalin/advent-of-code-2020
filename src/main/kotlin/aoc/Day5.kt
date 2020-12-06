package aoc

class Day5 : Day {

    override val debug = false

    override fun test() {
        assert(seatId("BFFFBBFRRR") == 567)
        assert(seatId("FFFBBBFRRR") == 119)
        assert(seatId("BBFFBBFRLL") == 820)
    }

    override fun run() {
        val rows = readLns("day5/input.txt")

        // Calculate all seat ids and sort
        val seatIds = rows.map { seatId(it) }.sorted()

        // Final entry is the highest seat id
        println("Part 1: ${seatIds.last()}")

        // Find a gap in the seat id's
        for (i in 0..(seatIds.size-2)) {
            if(seatIds[i+1] != seatIds[i] + 1) {
                println("Part 2: ${seatIds[i] + 1} is missing")
            }
        }
    }
}

fun seatId(code: String): Int {
    return 8 * row(code) + seat(code)
}

fun row(code: String): Int {
    return code.substring(0..6).replace('F', '0').replace('B', '1').toInt(2)
}

fun seat(code: String) : Int {
    return code.substring(7..9).replace('L', '0').replace('R', '1').toInt(2)
}

fun main() {
    Day5().test()
    Day5().run()
}

