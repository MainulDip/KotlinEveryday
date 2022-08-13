package sequences

fun main() {

    val hexNumberRegex = run {
        val digits = "0-9"
        val hexDigits = "A-Fa-f"
        val xToz = "X-Zx-z"
        val sign = "+-"

        // Regex("[$sign]?[$digits$hexDigits]+")
        // Regex("[+-]?[0-9A-Fa-f]+") // same
        Regex("[$sign]?[$digits$hexDigits$xToz]+")
    }

    for (match in hexNumberRegex.findAll("+123 -FFFF !%*& 88 XYZ")) {
        println(match.value)
    }

    // Regex().findAll("$string") will return kotlin.sequences.GeneratorSequence of all the matches starting from position 0 by default
    println(hexNumberRegex.findAll("+123 -FFFF !%*& 88 XYZ")) // kotlin.sequences.GeneratorSequence@.......
    println(hexNumberRegex.findAll("+123 -FFFF !%*& 88 XYZ").toList().map {it.value}) // [+123, -FFFF, 88, XYZ]
    println(hexNumberRegex.findAll("+123 -FFFF !%*& 88 XYZ").last().value) // XYZ
}