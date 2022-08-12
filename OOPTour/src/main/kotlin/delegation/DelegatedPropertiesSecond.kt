import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main() {
    println(lazyValue) // first call will print "Hello" and "Again"
    println(lazyValue) // second call will print only remembered get value which is -> "Again"

    println()
    lazyValue = "something else"
    println(lazyValue)
}

// val will make it immutable. for var, custom setValue needs to be created.
var lazyValue: String by lazy {
    println("Hello")

    "Again"
}

private operator fun <T> Lazy<T>.setValue(t: T?, property: KProperty<T?>, t1: T) {
//    TODO("Not yet implemented")
}
