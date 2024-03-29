import kotlin.properties.ReadOnlyProperty
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
var customSetterValue: Any? = null
var lazyValue: Any by lazy {
//    println("World")
}

private operator fun <T : Any> Lazy<T>.getValue(t: T?, property: KProperty<*>): Any {
    println("World")
    return customSetterValue?.toString() ?: "Again"
//    if(customSetterValue != null)
//        return customSetterValue as T
//    else
//        println("Testing")
//        return t as T
////    return t
}

private operator fun <T : Any> Lazy<T>.setValue(t: T?, property: KProperty<*>, t1: T) {
    customSetterValue = t1
}
