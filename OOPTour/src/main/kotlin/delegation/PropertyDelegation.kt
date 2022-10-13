package delegation

import kotlin.reflect.KProperty

class Delegate {
    private var value: String? = null // lateinit will not work here, as we are calling the getValue first
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        if (value == null ) value = "Not Yet Set"
        // return "$thisRef, thank you for delegating '${property.name}' to me! and the value is \"$value\""
        return "value = \"$value\"";
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
        this.value = "$value"
    }


    // method's other than getValue() and setValue() is not accessible to delegated properties.
    // property delegation will only be mapped with properties get() and set() methods.
}

class Example {
//    var p: String by Delegate()
    var p by Delegate()
}

fun main(){
    var e = Example()
    println(e.p) // value = "Not Yet Set"
    e.p = "Hello World" // Hello World has been assigned to 'p' in delegation.Example@.......
    println(e.p) // value = "Hello World"
}