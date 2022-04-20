package dataSealedEnumClasses

fun main(args: Array<String>) {
    println(enumValueOf<Direction>("NORTH"))
    /**
     * Every enum constant has properties for obtaining its name and position in the enum class declaration
     * val name: String
     * val ordinal: Int
     */
    println(enumValues<Direction>().joinToString(separator = " ") { it.name })

    println(enumValues<Color>().joinToString(separator = " ") { it.name })

    println(Color.RED.rgb);
    println(ProtocolState.WAITING.signal())

    println(ProtocolState.TALKING.signal())

    /**
     * To implement Function we need to abstract/override that
     * Calling the IntArithmetics enum
     */

    val a = 13
    val b = 31
    for (f in IntArithmetics.values()) {
        println("$f($a, $b) = ${f.apply(a, b)}")
    }
}

enum class Direction { NORTH, SOUTH, WEST, EAST }

enum class Color(val rgb: String) {
    RED("0xFF0000"),
    GREEN("0x00FF00"),
    BLUE("0x0000FF")
}

enum class ProtocolState {
    WAITING {
        override fun signal() = TALKING
    },

    TALKING {
        override fun signal() = WAITING
    }; // If the enum class defines any members, separate the constant definitions from the member definitions with a semicolon.

    abstract fun signal(): ProtocolState // member
}

enum class IntArithmetics {
    PLUS {
        override fun apply(t: Int, u: Int): Int = t + u
    },
    TIMES {
        override fun apply(t: Int, u: Int): Int =t * u
    };

    // make it abstract first or if implementing from another interface then override implementation first then override again inside members (try to keep the first override meaningful)
    abstract fun apply(t: Int, u: Int) : Int
}



