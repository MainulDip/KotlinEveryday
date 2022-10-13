package delegation

import kotlin.reflect.KProperty

fun main() {
    var someval by delegatedValue {
        SomeState("Something 1")
    }
    println(someval)
    someval = "Testing"
    println(someval)

    var anotherValue by delegatedValue {
        SomeState("Something 2")
    }
    println(anotherValue)
}

fun delegatedValue(call: () -> SomeState): SomeState {
    return call()
}


class SomeState (var value: String) {
//    var value: String? = null
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        if (value == null ) value = "Not Yet Set"
        // return "$thisRef, thank you for delegating '${property.name}' to me! and the value is \"$value\""
        return "value = \"$value\"";
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
        this.value = "$value"
    }
}


/*
* Object can be used for singleton or state storage
* */
object SomeStateObj {
    var value: String? = null
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        if (value == null ) value = "Not Yet Set"
        // return "$thisRef, thank you for delegating '${property.name}' to me! and the value is \"$value\""
        return "value = \"$value\"";
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
        this.value = "$value"
    }
}