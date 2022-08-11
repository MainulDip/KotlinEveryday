package delegation

fun main() {
    println(lazyValue)
    println(lazyValue)

    println()

    lazyValue = "Something"
    println(lazyValue) // will only print "Something" as remembered (getValue())
}

var lazyValue: String = run {
    println("Hello")
    "Again"
}

// Print These
// Hello
// Again
// Again

// Something