package aoc

import java.io.File

interface Day {

    val debug: Boolean

    // Must be overridden
    fun run()

    // Can be overridden
    fun test() {}

    // Read input as lines
    fun readLns(path: String): List<String> {
        return File(ClassLoader.getSystemResource(path).file).readLines()
    }

    // Read groups of input separated by empty lines
    fun applyToGroupOfLines(path: String, function: (List<String>) -> Unit) {
        val rows = readLns(path).toMutableList()
        rows.add("")

        var group = mutableListOf<String>()

        rows.forEach { row ->
            if (row.isBlank()) {
                function(group)
                group = mutableListOf()
            } else {
                group.add(row)
            }
        }
    }

    fun debug(str: String) {
        if (debug) {
            println("   >>> $str")
        }
    }
}
