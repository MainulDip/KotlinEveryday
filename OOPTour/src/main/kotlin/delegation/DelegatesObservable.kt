package delegation

import kotlin.properties.Delegates

fun dl() {
    var value: String? = null
    lazyValue
}

fun main() {
}

// <no name>
// prop : var delegation.User.name: kotlin.String || old value: <no name> || new value : first
// prop : var delegation.User.name: kotlin.String || old value: first || new value : second
// prop : var delegation.User.name: kotlin.String || old value: second || new value : third