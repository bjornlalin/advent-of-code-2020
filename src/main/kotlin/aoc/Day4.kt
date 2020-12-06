package aoc

class Day4 : Day {

    override val debug = false

    private val required = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" /*, "cid" */)

    override fun run() {
        var rows = readLns("day4/input.txt")

        // Add trailing empty line
        rows = rows.toMutableList()
        rows.add("")

        var nValid1 = 0
        var nValid2 = 0

        var passport = mutableMapOf<String, String>()

        rows.forEach { row ->
            if(row.isBlank()) {

                nValid1 += if (allRequiredFields(passport)) 1 else 0
                nValid2 += if (allRequiredFields(passport) && validateFields(passport)) 1 else 0

                // Reset passport
                passport = mutableMapOf()
            } else {
                row.split(" ").forEach { passport[it.split(":")[0]] = it.split(":")[1] }
            }
        }

        println("Part 1: $nValid1")
        println("Part 2: $nValid2")
    }

    fun allRequiredFields(passport: Map<String, String>): Boolean = passport.keys.intersect(required).size >= 7

    fun validateFields(passport: Map<String, String>): Boolean {
        passport["byr"]?.let {
            val ok = """\d{4}""".toRegex() matches it && it.toInt() >= 1920 && it.toInt() <= 2002
            if (!ok) return false
        }

        passport["iyr"]?.let {
            val ok = """\d{4}""".toRegex() matches it && it.toInt() >= 2010 && it.toInt() <= 2020
            if (!ok) return false
        }

        passport["eyr"]?.let {
            val ok = """\d{4}""".toRegex() matches it && it.toInt() >= 2020 && it.toInt() <= 2030
            if (!ok) return false
        }

        passport["hgt"]?.let {
            val ok = ("""\d{3}cm""".toRegex() matches it && it.substring(0, 3).toInt() >= 150 && it.substring(0, 3).toInt() <= 193) ||
                    ("""\d{2}in""".toRegex() matches it && it.substring(0, 2).toInt() >= 52 && it.substring(0, 2).toInt() <= 76)
            if (!ok) return false
        }

        passport["hcl"]?.let {
            val ok = ("""#[a-f|0-9]{6}""".toRegex() matches it)
            if (!ok) return false
        }

        passport["ecl"]?.let {
            val ok = (it in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth"))
            if (!ok) return false
        }

        passport["pid"]?.let {
            val ok = ("""\d{9}""".toRegex() matches it)
            if (!ok) return false
        }

        return true
    }
}


fun main() {
    Day4().run()
}

