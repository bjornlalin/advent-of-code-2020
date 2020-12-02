package aoc

import java.io.File

interface Day {

    /* To be implemented */
    fun run()

    /* Read input as lines */
    fun readLns(path: String): List<String> {
        return File(ClassLoader.getSystemResource(path).file).readLines()
    }
}
