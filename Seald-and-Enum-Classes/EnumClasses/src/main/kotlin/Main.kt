package org.example

import java.util.function.BinaryOperator
import java.util.function.IntBinaryOperator

/**
 * Enum ordinal is the Enum's constant's position on the Enum
 * As any enum constants are instances of the surrounding Enum class
 * to declare a method and call it by enum.constant.method() we need to make it abstract
 * then the method needs to be overridden
 */

enum class IntArithmetics  {
    PLUS { // PLUS is an anonymous enum Constant class (or anonymous class constant)
        override fun sth(t: Int, u: Int): Int = t + u
    },
    TIMES { // Same as PLUS, its an instances of the surrounding Enum class
        override fun sth(t: Int, u: Int): Int = t * u
    };

    abstract fun sth(t: Int, u: Int): Int
}

fun main() {
    println("${IntArithmetics.PLUS.ordinal}, ${IntArithmetics.TIMES.ordinal}") // 0, 1
    println(IntArithmetics.entries) // [PLUS, TIMES]

    val a = 13
    val b = 31

    println(IntArithmetics.PLUS.sth(a,b)) // 44
    println(IntArithmetics.TIMES.sth(a,b)) // 403

    for (f in IntArithmetics.entries) {
        println("$f($a, $b) = ${f.sth(a,b)}")
    }
    // PLUS(13, 31) = 44
    // TIMES(13, 31) = 403
}
