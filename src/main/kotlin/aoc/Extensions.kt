package aoc

fun String.parseStrings(sep: Char = ' '): List<String> {
    return this.split(sep)
}

fun String.parseLongs(): List<Long> {
    return this.parseStrings().map { it.toLong() }
}

fun String.parseInts(): List<Int> {
    return this.parseStrings().map { it.toInt() }
}
