package delegation

import kotlin.properties.Delegates

class User {
    var name: String by Delegates.observable("<no name>") {
            prop, old, new ->
        println("prop : ${prop} || old value: $old || new value : $new")
    }
}

fun main() {
    val user = User()
    println(user.name)
    user.name = "first"
    user.name = "second"
    user.name = "third"
}

// <no name>
// prop : var delegation.User.name: kotlin.String || old value: <no name> || new value : first
// prop : var delegation.User.name: kotlin.String || old value: first || new value : second
// prop : var delegation.User.name: kotlin.String || old value: second || new value : third