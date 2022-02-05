class ExploringLambda {
    fun main(args: Array<String>) {
        println("Hello World!")

        // Try adding program arguments via Run/Debug configuration.
        // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
        println("Program arguments: ${args.joinToString()}")
    }
}

fun Collection<Int>.customFold(
    initial: Int,
    combine: (acc: Int, nextElement: Int) -> Int
): Int {
    var accumulator: Int = initial
    for (element: Int in this) {
        accumulator = combine(accumulator, element)

    }
    println("This will print last")
    return accumulator
}

fun main(args: Array<String>) {
    val items = listOf(1, 2, 3, 4, 5)

// Lambdas are code blocks enclosed in curly braces.
    val value = items.customFold(0, {
        // When a lambda has parameters, they go first, followed by '->'
            acc: Int, next: Int ->
        print("acc = $acc, next = $next, ")
        val result = acc + next
        println("results = $result")
        // The last expression in a lambda is considered the return value:
        result
    })
    println("Valy => $value")

// Parameter types in a lambda are optional if they can be inferred:
//    val joinedToString = items.fold("Elements:", { acc, i -> acc + " " + i })

// Function references can also be used for higher-order function calls:
//    val product = items.fold(1, Int::times)
}