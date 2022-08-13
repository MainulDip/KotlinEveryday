package sequences

fun main() {

    // basic sequence
    val numbers = listOf("one", "two", "three", "four")
    val numbersSequence = numbers.asSequence()
    println(numbersSequence.toList()) // [one, two, three, four]

    // infinite sequence
    // if not blocked by else, sequence is infinite
    val oddNumbers = generateSequence(1) { it + 2 } // `it` is the previous element
    val s5 = oddNumbers.take(5).toList()
    println(oddNumbers.take(5).toList()) // [1, 3, 5, 7, 9]
    println(s5.count()) // 5
    //println(oddNumbers.count()) // error: the sequence is infinite

    //finite sequence
    val oddNumbersLessThan10 = generateSequence(1) { if (it < 8) it + 2 else null }
    println(oddNumbersLessThan10.count()) // 5
    println(oddNumbersLessThan10) // kotlin.sequences.GeneratorSequence@.......

}