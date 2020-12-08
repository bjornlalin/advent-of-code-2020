package aoc

class Day7 : Day {

    override val debug = true

    override fun test() {
        val bags = parseBags(readLns("day7/test.txt"))
        println("Part 1 (test input): ${countNumBagsWhichCanContain("shiny gold", bags)}")
        println("Part 2 (test input): ${countBags("shiny gold", 1, bags)}")
    }

    override fun run() {
        val bags = parseBags(readLns("day7/input.txt"))
        println("Part 1: ${countNumBagsWhichCanContain("shiny gold", bags)}")
        println("Part 2: ${countBags("shiny gold", 1, bags)}")
    }

    // This builds the map of colored bags -> list of contained color bags (incl. number)
    private fun parseBags(rows: List<String>): Map<String, Map<String, Int>> {

        val bags = mutableMapOf<String, Map<String, Int>>()
        val pattern = """\d+ \w+ \w+ """.toRegex()

        rows.forEach { row ->
            // Parse color of bag
            val color = "${row.split(' ')[0]} ${row.split(' ')[1]}"

            // Parse bags contained within this bag
            val bagsInBag = mutableMapOf<String, Int>()
            pattern.findAll(row).forEach { match ->
                val _matched = match.groups[0]!!.value.split(' ')
                val _color = "${_matched[1]} ${_matched[2]}"
                val _count = _matched[0].toInt()

                bagsInBag[_color] = _count
            }

            bags[color] = bagsInBag
        }

        // Return an immutable copy
        return bags.toMap()
    }

    // Part 2: Count the number of bags contained in a bag of color 'bagColor'
    private fun countBags(bagColor: String, mul: Int, bags: Map<String, Map<String, Int>>): Int {
        val containedBags = bags[bagColor] ?: throw Exception("CORRUPT")
        return containedBags.map { (bagColor, n) -> ((n + countBags(bagColor, n, bags)) * mul) }.sum()
    }

    // Part 1: Count number of bags which can ultimately contain a bag of color 'color'
    private fun countNumBagsWhichCanContain(color: String, bags: Map<String, Map<String, Int>>): Int {
        var cnt = 0
        bags.keys.forEach {
            if (canContain(it, color, bags)) {
                cnt += 1
            }
        }

        return cnt
    }

    private fun canContain(bagColor: String, color: String, bags: Map<String, Map<String, Int>>): Boolean {
        val containedBags = bags[bagColor] ?: throw Exception("CORRUPT")

        if (containedBags.containsKey(color)) {
            return true
        }

        for (c in containedBags.keys) {
            if(canContain(c, color, bags)) {
                return true
            }
        }

        return false
    }
}

fun main() {
    Day7().test()
    Day7().run()
}
