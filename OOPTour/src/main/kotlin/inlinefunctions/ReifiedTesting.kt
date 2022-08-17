package inlinefunctions

// Declaring Inline function
inline fun <reified T> myExample(name: T) {
    println("\nName of your website -> "+name)
    println("\nType of myClass: ${T::class.java}")
}

inline fun <reified T> simpleTestReified(a: Any) {
    println()
    if (a is T) println("\"$a\" = \"${a::class}\" and T = \"${T::class}\" are same type")
}

// IDE error: Cannot check for instance of erased type: T -> in the if (a is T) || Will suggest to make inline reified
//fun <T : String> simpleTestReifiedS(a: Any) {
//    println()
//    if (a is T) println("\"$a\" = \"${a::class}\" and T = \"${T::class}\" are same type")
//}

fun main() {

    // calling func() with String
    myExample<String>("www.websolverpro.com")

    // calling func() with Int value
    myExample<Int>(100)

    // calling func() with Long value
    myExample<Long>(1L)

    simpleTestReified<String>("This is String")

}