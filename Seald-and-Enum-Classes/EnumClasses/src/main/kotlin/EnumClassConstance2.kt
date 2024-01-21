package org.example

enum class IntArithmetics2  {
    PLUS, TIMES;
    fun calculate(t: Int, u: Int): Int {
        return when (this) {
            PLUS -> t + u
            TIMES -> t * u
        }
    }
}

fun main() {

    val a = 13
    val b = 31

    println(IntArithmetics2.PLUS.calculate(a,b)) // 44
    println(IntArithmetics2.TIMES.calculate(a,b)) // 403

    for (f in IntArithmetics2.entries) {
        println("$f($a, $b) = ${f.calculate(a,b)}")
    }
    // PLUS(13, 31) = 44
    // TIMES(13, 31) = 403
}