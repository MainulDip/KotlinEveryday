package org.example

enum class IntArithmetics  {
    PLUS { // PLUS is an anonymous enum Constant class (or anonymous class constant)
        override fun calculate(t: Int, u: Int): Int = t + u
    },
    TIMES { // Same as PLUS, it's an instances of the surrounding Enum class
        override fun calculate(t: Int, u: Int): Int = t * u
    };

    abstract fun calculate(t: Int, u: Int): Int
}

/**
 * Can also be written like this, this EnumClassConstant2 example
enum class IntArithmetics2  {
    PLUS, TIMES;
    fun calculate(t: Int, u: Int): Int {
        return when (this) {
            PLUS -> t + u
            TIMES -> t * u
        }
    }
}
*/

fun main() {
    println("${IntArithmetics.PLUS.ordinal}, ${IntArithmetics.TIMES.ordinal}") // 0, 1
    println(IntArithmetics.entries) // [PLUS, TIMES]

    val a = 13
    val b = 31

    println(IntArithmetics.PLUS.calculate(a,b)) // 44
    println(IntArithmetics.TIMES.calculate(a,b)) // 403

    for (f in IntArithmetics.entries) {
        println("$f($a, $b) = ${f.calculate(a,b)}")
    }
    // PLUS(13, 31) = 44
    // TIMES(13, 31) = 403
}