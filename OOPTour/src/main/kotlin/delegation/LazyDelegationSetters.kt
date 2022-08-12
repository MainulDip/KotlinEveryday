package delegation

import kotlin.reflect.KProperty

fun main() {
    println(lazyValue1)
    println(lazyValue1)

    println()
    lazyValue1 = "something else"
    println(lazyValue1)
}

// val will make it immutable. for var, custom setValue needs to be created.
var customSetterValue: Any? = null
private var lazyValue1: Any by lazy {
//    println("World")
//    "asdf"
}

private operator fun <T : Any> Lazy<T>.getValue(t: T?, property: KProperty<*>): Any {
    println("World")
    return customSetterValue?.toString() ?: "Again"
//    if(customSetterValue != null)
//        return customSetterValue as T
//    else
//        println("Testing")
//        return t as T
}

private operator fun <T : Any> Lazy<T>.setValue(t: T?, property: KProperty<*>, t1: T) {
    customSetterValue = t1
}
