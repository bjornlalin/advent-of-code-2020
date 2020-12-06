package aoc

fun main() {

    // Add the list of all solutions here...
    val all = mapOf(
            "Day 1" to Day1(),
            "Day 2" to Day2(),
            "Day 3" to Day3(),
            "Day 4" to Day4(),
            "Day 5" to Day5()
    )

    all.forEach { (name, impl) ->
        println(name)
        println("----------------------------")
        impl.run()
        println("----------------------------")
        println()
    }
}