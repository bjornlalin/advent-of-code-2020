package aoc

class Day11 : Day {

    override val debug = false

    override fun test() {
        solveForInput("day11/test.txt")
    }

    override fun run() {
        solveForInput("day11/input.txt")
    }

    private fun solveForInput(path: String) {
        val seats = parseLayout(readLns(path))

        val layout1 = SeatLayout(seats)
        val rounds1 = layout1.simulate(false)
        println("Part 1: After $rounds1 rounds there are ${layout1.countSeats('#')} occupied seats")

        val layout2 = SeatLayout(seats)
        val rounds2 = layout2.simulate(true)
        println("Part 2: After $rounds2 rounds there are ${layout2.countSeats('#')} occupied seats")
    }

    // Parse input (add border to it)
    private fun parseLayout(rows: List<String>): Array<CharArray> {
        return Array(rows.size + 2) {
            when (it) {
                0 -> CharArray( rows[0].length + 2) { '*' }
                rows.size + 1 -> CharArray( rows[0].length + 2) { '*' }
                else -> charArrayOf('*') + rows[it-1].toCharArray() + charArrayOf('*')
            }
        }
    }
}

class SeatLayout(var seats: Array<CharArray>) {

    fun simulate(part2:Boolean = false): Int {

        // Keep track of if something changed
        var changed = true
        var rounds = 0

        while(changed) {
            changed = false // At beginning of each round, there are no changes
            val seatsCopy = Array(seats.size) { CharArray(seats[0].size) { '*' } } // Make a copy to perform operations on

            for (r in 1 until seats.size - 1) {
                for (c in 1 until seats[0].size - 1) {
                    when (seats[r][c]) {
                        '.', '*' -> {                                   // Floor or boundary - do nothing
                            seatsCopy[r][c] = seats[r][c]
                        }
                        'L' -> {                                        // Free - should we swap it?
                            if (shouldTakeFreeSeat(r, c, part2)) {
                                changed = true
                                seatsCopy[r][c] = '#'
                            } else {
                                seatsCopy[r][c] = 'L'
                            }
                        }
                        '#' -> {                                        // Occupied - should we swap it?
                            if (shouldFreeOccupiedSeat(r, c, part2)) {
                                changed = true
                                seatsCopy[r][c] = 'L'
                            } else {
                                seatsCopy[r][c] = '#'
                            }
                        }
                    }
                }
            }

            seats = seatsCopy   // Replace seat layout with new one after all rules are applied
            rounds += 1     // Count number of rounds so far
        }

        return rounds
    }

    private fun shouldTakeFreeSeat(r:Int, c:Int, part2: Boolean): Boolean {
        return when (part2) {
            true -> !visible(r, c).contains('#')
            else -> !adjacent(r, c).contains('#')
        }
    }

    private fun shouldFreeOccupiedSeat(r:Int, c:Int, part2: Boolean): Boolean {
        return when (part2) {
            true -> visible(r, c).filter { it == '#' }.size >= 5
            else -> adjacent(r, c).filter { it == '#' }.size >= 4
        }
    }

    // Get a list of next visible seats in each direction
    private fun visible(row:Int, col:Int): List<Char> {
        val dirs = listOf(Pair(0, 1), Pair(0,-1), Pair(1, 0), Pair(-1, 0), Pair(-1, -1), Pair(-1, 1), Pair(1, -1), Pair(1, 1))

        return dirs.mapNotNull {
            var r = row + it.first
            var c = col + it.second

            while (r > 0 && r < seats.size-1 && c > 0 && c < seats[0].size-1) {
                if(seats[r][c] == '#' || seats[r][c] == 'L') {
                    return@mapNotNull seats[r][c]
                }

                r += it.first
                c += it.second
            }

            return@mapNotNull null
        }
    }

    // Get a list of adjacent seats
    private fun adjacent(r: Int, c: Int): List<Char> {
        return listOf(seats[r-1][c-1], seats[r-1][c], seats[r-1][c+1], seats[r][c-1], seats[r][c+1], seats[r+1][c-1], seats[r+1][c], seats[r+1][c+1]).filter { it != '*' }
    }

    // Count number of occupied seats in seating
    fun countSeats(symbol: Char): Int {
        return seats.flatMap { it.filter { it == symbol } }.size
    }
}

fun main() {
    Day11().test()
    Day11().run()
}
