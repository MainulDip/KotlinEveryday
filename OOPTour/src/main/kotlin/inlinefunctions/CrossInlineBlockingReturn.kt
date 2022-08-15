package inlinefunctions

inline fun inlineFunction(block: () -> String): String {
    return block()
}

inline fun crossInlineFunction(crossinline block: () -> String): String {
    return block()
}

fun foo(): String {
    return inlineFunction {
        return "Hello" // return is allowed here because of not declaring "crossinline"
    }
}

fun baz(): String {
    return crossInlineFunction {
//        return "Hello" // 'return' is not allowed here because of the crossinline declaration
        "Hello From crossInline declaration"
    }
}

fun main() {
    println(foo())
    println(baz())
}