package scopefunctions

fun main() {

    val numbers = mutableListOf("one", "two", "three", "four", "five")

    // again some map filter
    println(numbers.map{it.length}) // [3, 3, 5, 4, 4]
    println(numbers.map{it.length}.filter{it > 3}) // [5, 4, 4]
    println(numbers.filter{it != "five"}) // [one, two, three, four]
    println()

    // without let scope function
    val resultList = numbers.map { it.length }.filter { it > 3 }
    println(resultList)
    println()

    // with let scope funciton
    numbers.map { it.length }.filter { it > 3 }.let {
        println(it) // [5, 4, 4]
        println(it.filter{it != 5}) // [4, 4]
        println(it.filter{it != 4}) // [5]
        println("something else") // something else
        // and more function calls if needed
    }

    // If the code block contains a single function invocation, method reference (::) can be used instead of the lambda
    numbers.map { it.length }.filter { it > 3 }.let(::println)

    // null probability and safe-call operator "?" with "let"
    fun processNonNullString(str: String) {}
    val str: String? = "Hello"
    //processNonNullString(str)       // compilation error: str can be null
    val length = str?.let {
        println("let() called on $it") // let() called on Hello
        processNonNullString(it)    // OK: 'it' is not null inside '?.let { }'
        println(it.length) // 5
    }
}