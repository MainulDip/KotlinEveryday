package inlinefunctions

// Declaring Inline function
inline fun <reified T> myExample(name: T) {
    println("\nName of your website -> "+name)
    println("\nType of myClass: ${T::class.java}")
}

fun main() {

    // calling func() with String
    myExample<String>("www.websolverpro.com")

    // calling func() with Int value
    myExample<Int>(100)

    // calling func() with Long value
    myExample<Long>(1L)

}