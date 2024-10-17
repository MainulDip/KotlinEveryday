### Sealed class and Constructor:
A sealed class itself is always an abstract class, and as a result, can't be instantiated directly. It may contain or inherit constructors. These constructors aren't for creating instances of the sealed class itself but for its subclasses. 

```kotlin
sealed class Error(val message: String) {
    class NetworkError : Error("Network failure")
    class DatabaseError : Error("Database cannot be reached")
    class UnknownError : Error("An unknown error has occurred")
}

fun main() {
    val errors = listOf(Error.NetworkError(), Error.DatabaseError(), Error.UnknownError())
    errors.forEach { println(it.message) }
}

// Network failure
// Database cannot be reached
// An unknown error has occurred
```

Using Object Inside Sealed Class

```kotlin
sealed class Error(val message: String) {
    object NetworkError : Error("Network failure")
    object DatabaseError : Error("Database cannot be reached")
    object UnknownError : Error("An unknown error has occurred")
}

fun main() {
    val errors = listOf(Error.NetworkError, Error.DatabaseError, Error.UnknownError)
    errors.forEach { println(it.message) }
}

// Network failure
// Database cannot be reached
// An unknown error has occurred
```

### Sealed class and Interface:
https://kotlinlang.org/docs/sealed-classes.html