package allaboutlambda

fun main() {
    println("---------------basicLambda1----------------------")
    println(basicLambda1("Test") { x, y -> "$x $y" })

    println("---------------sideEffect Lambda----------------------")
    val getVal = sideEffectLambda("Side", "Effect") { whatever ->
        println("Printing $whatever as Side Effect, Voila")
    }
    println(getVal)
}

fun basicLambda1 (concat: String, fun1: (String, String) -> String): String {
    val s = "Hello World"
    return fun1(s, concat)
}

fun sideEffectLambda ( p1: String, p2: String, p3: (String) -> Unit ): String {
    val someString = "Some Calculation: $p1 $p2" // do some calculation here
    p3 (someString)
    return "$p1 $p2"
}