package dataSealedEnumClasses

import kotlin.math.absoluteValue

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
}

enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

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
    };

    abstract fun signal(): ProtocolState
}




