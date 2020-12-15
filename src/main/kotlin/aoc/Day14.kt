package aoc

import java.util.*

class Day14 : Day {

    override val debug = false

    override fun test() {
        part1("day14/test.txt") // Expected: 165
        part2("day14/test2.txt") // Expected: 208
    }

    override fun run() {
        part1("day14/input.txt")
        part2("day14/input.txt")
    }

    private fun part1(path: String) {
        val input = readLns(path)
        val mem = mutableMapOf<Long, Long>()
        var mask: CharArray? = null

        input.forEach {
            if(it.startsWith("mask")) {
                mask = it.split('=')[1].trim().toCharArray()
                mask!!.reverse()
            } else {
                val address = it.substring(it.indexOf('[')+1, it.indexOf(']')).toLong()
                val value = it.split('=')[1].trim().toLong()

                mem[address] = applyMaskToValue(value, mask!!)
            }
        }

        println("Part 1: ${mem.values.sum()}")
    }

    private fun applyMaskToValue(value: Long, mask: CharArray): Long {
        val b = BitSet.valueOf(arrayOf(value).toLongArray())
        mask.indices.forEach() {
            when (mask[it]) {
                '1' -> b.set(it, true)
                '0' -> b.set(it, false)
            }
        }

        return if (b.isEmpty) 0L else b.toLongArray()[0]
    }


    private fun part2(path: String) {
        val input = readLns(path)
        val mem = mutableMapOf<Long, Long>()
        var mask: CharArray? = null

        input.forEach {
            if(it.startsWith("mask")) {
                mask = it.split('=')[1].trim().toCharArray()
                mask!!.reverse()
            } else {
                val address = it.substring(it.indexOf('[')+1, it.indexOf(']')).toLong()
                val value = it.split('=')[1].trim().toLong()

                // Make a copy of the mask as starting point for the fluctuating address
                val fluctAddress = mask!!.copyOf()

                // Generate the fluctuating address (all '0' become '1' if the mask has a '1', all 'X' remain
                val b = BitSet.valueOf(arrayOf(address).toLongArray())
                for (i in 0..36) {
                    if (b.get(i) && fluctAddress[i] == '0') {
                        fluctAddress[i] = '1'
                    }
                }

                // Part 2
                applyValueToFluctuatingAddress(mem, value, fluctAddress)
            }
        }

        println("Part 2: ${mem.values.sum()}")
    }

    // Recursive function - split on each 'X' and replace that address with '0' and '1'
    fun applyValueToFluctuatingAddress(mem: MutableMap<Long, Long>, value: Long, fluctuatingAddress: CharArray) {

        if (!fluctuatingAddress.contains('X')) {
            // All 'X' have been replaced, convert to long value and write value to the address

            // Generate a bitset from character array of '0':s and '1':s
            val bits = BitSet(64)
            fluctuatingAddress.indices.forEach {
                bits.set(it, fluctuatingAddress[it] == '1')
            }

            val address = if (bits.isEmpty) 0L else bits.toLongArray()[0]
            mem[address] = value
            debug("writing $value to $address")
        } else {
            // Still a fluctuating address - replace the next 'X' with proper values and solve for 2 sub-problems

            val with0 = fluctuatingAddress.copyOf()
            with0[with0.indexOf('X')] = '0'
            applyValueToFluctuatingAddress(mem, value, with0)

            val with1 = fluctuatingAddress.copyOf()
            with1[with1.indexOf('X')] = '1'
            applyValueToFluctuatingAddress(mem, value, with1)
        }
    }
}

fun main() {
    Day14().test()
    Day14().run()
}