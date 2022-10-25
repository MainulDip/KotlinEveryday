package allaboutlambda

fun main() {
    println("---------------basicLambda1----------------------")
    println(basicLambda1("Test") { x, y -> "$x $y" })
}

fun basicLambda1 (concat: String, fun1: (String, String) -> String): String {
    val s = "Hello World"
    return fun1(s, concat)
}