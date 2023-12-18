### Statement vs Expression:
```kotlin
/**
* using `when` as expression. In this case IDE does not auto suggest all the possibilities. But with `when` as statement, IDE will auto suggest
* adding extension `T.exhaustive` will help IDE to auto suggest
* `expression` -> When a block of code returns value
* `statement` does not return value, rather do side effect/s (like modifying variable/s). 
* as a whole, most of the things are `statement`. Inside statement, there can be `expression` or can't
* here the when block returns a value, thats why its an expression
* But as a whole (including assignment operation), its a statement 
*/

// using as expression, as it returns a value
val getGender: String = when(person.gender){
    is Person.Gender.Male -> // return a value
    is Person.Gender.Female -> // return a value
}.exhaustive

val <T> T.exhaustive : T
    get() = this

sealed class Gender {
    object Male: Gender()
    object Female: Gender()
}

// using `when` as statement, as it doesn't return value, do side effects
when(person.gender){
    is Person.Gender.Male -> { /* Do something */ }
    is Person.Gender.Female -> { /* Do something */ }
}
```

### Mimicking .fold with Generics and apply Lambda:
```kotlin
/**
 * Generics and Lambda
 * this is the signature of higher-order function fold, just implemented as different name
 */
fun <T, R> Collection<T>.processAddition(
    initial: R,
    combine: (R, T) -> R /* can write this `combine: (acc: R, element: T) -> R` also, params name is optional here */
): R {
    var accumulator: R = initial
    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }
    return accumulator
}


fun main() {
    
    val items = listOf(1, 2, 3, 4, 5)
    
    // Lambdas are code blocks enclosed in curly braces.
    items.processAddition(0, { 
        // When a lambda has parameters, they go first, followed by '->'
        acc: Int, i: Int -> 
        print("acc = $acc, i = $i, ") 
        val result = acc + i
        println("result = $result")
        // The last expression in a lambda is considered the return value:
        result
    })
    
    // Parameter types in a lambda are optional if they can be inferred:
    val joinedToString = items.processAddition("Elements:", { acc, i -> acc + " " + i })
    
    // Function references can also be used for higher-order function calls:
    val product = items.processAddition(1, Int::times)
    
    println("joinedToString = $joinedToString")
    println("product = $product")
}

// returns
// acc = 0, i = 1, result = 1
// acc = 1, i = 2, result = 3
// acc = 3, i = 3, result = 6
// acc = 6, i = 4, result = 10
// acc = 10, i = 5, result = 15
// joinedToString = Elements: 1 2 3 4 5
// product = 120
```