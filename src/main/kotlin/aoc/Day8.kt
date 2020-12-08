package aoc

import kotlin.system.exitProcess

data class Instr(val op: String, val value: Int)

fun parseInstr(str: String): Instr {
    val op = str.split(' ')[0]
    val value = str.split(' ')[1].toInt()
    return Instr(op, value)
}

class Comp(program: List<String>) {

    var pos = 0
    var acc = 0
    private val instructions = mutableListOf<Instr>()
    private val alreadyExecuted = mutableSetOf<Int>()

    init {
        program.forEach { instructions.add(parseInstr(it)) }
    }

    fun run(): Int {
        while(pos in 0 until instructions.size) {

            // Loop detection
            if (pos in alreadyExecuted) {
                return -1
            } else {
                alreadyExecuted += pos
            }

            executeNext()
        }

        return 0
    }

    private fun executeNext() {

        // Execute instruction
        val instr = instructions[pos]
        when (instr.op) {
            "acc" -> {
                acc += instr.value
                pos += 1
            }
            "jmp" -> {
                pos += instr.value
            }
            "nop" -> {
                pos += 1
            }
            else -> {
                throw Exception("Illegal instruction")
            }
        }
    }
}

class Day8 : Day {

    override val debug = true

    override fun test() {
        val comp = Comp(readLns("day8/test.txt"))
        assert(comp.run() == -1)
        assert(comp.pos == 5)
    }

    override fun run() {

        val program = readLns("day8/input.txt")

        // Part 1
        val comp1 = Comp(program)
        assert(comp1.run() == -1)

        println("Part 1: ${comp1.acc}")

        // Part 2
        val indicesToMutate: List<Int> = program.withIndex().filter { it.value.startsWith("jmp") || it.value.startsWith("nop") }.map { it.index }
        for (index in indicesToMutate) {
            val copy = program.toMutableList()
            if (copy[index].startsWith("jmp"))
                copy[index] = copy[index].replace("jmp", "nop")
            else if (copy[index].startsWith("nop"))
                copy[index] = copy[index].replace("nop", "jmp")

            val comp = Comp(copy)
            if (comp.run() == 0) {
                println("Part 2: ${comp.acc}")
                exitProcess(0)
            }
        }
    }
}

fun main() {
    Day8().test()
    Day8().run()
}