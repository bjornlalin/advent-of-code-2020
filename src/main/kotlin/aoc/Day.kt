package aoc

import java.io.File

interface Day {

    val DEBUG: Boolean

    /* To be implemented */
    fun run()

    /* Read input as lines */
    fun readLns(path: String): List<String> {
        return File(ClassLoader.getSystemResource(path).file).readLines()
    }

    fun debug(str: String) {
        if (DEBUG) {
            println("   >>> $str")
        }
    }
}
