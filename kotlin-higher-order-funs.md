## Higher Order Functions Survival Docs:
It's a mini personalised doc for some of the kotlin's higher order functions.

### map vs flatMap:
```kotlin
// map()
fun <T, R> Iterable<T>.map(transform: (T) -> R): List<R>

// flatMap()
fun <T, R> Iterable<T>.flatMap(transform: (T) -> Iterable<R>): List<R>
```
https://www.baeldung.com/kotlin/map-vs-flatmap.

### Iterable.flatten()