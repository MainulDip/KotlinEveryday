## Kotlin Advanced Language Tour Overview
It's a personalized guide and quick tour of the day to day used kotlin language advanced/non-basic syntaxes and features. Like Kotlin DSL (Builders), Context and Extention functions, HigherOrder etc.

### Context and Extention function
```kt
fun call(greet: String.(String) -> Unit) {
    // greet("Hello", " Dolly") // greet's 1st parameter is the context and last param is it's native parameter.
    "Hello".greet("Dolly") // can be also written this way
}

// define the function signature using lambda
call { name ->
    println("${this.toUpperCase()} $name") // Print => HELLO Dolly
}

// this is somewhat similar with Javascript's call(), apply() or bind() method
```