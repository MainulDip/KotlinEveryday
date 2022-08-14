## Kotlin OOP Part 2 Overview:
Continued From oop-tour-kotlin.md. This markdow file provides mini docs for following topics:
- [Companio Object | Static Method (Java)](#companion-static)
- [Primary & secondary contructors](#primary-secondary-constructor)
- [Invariance, Covariance, Contravariance](#generic-variances)
- [Generics Variance (in/out) or variance annotation](#generics-in-out)
- [Star Projection (Generics/Foo<*>):](#star-projection)
- [Delegated Properties (some-more):](#more-on-delegated-properties)

### <a name="companion-static"> java/kotlin Static Method || companion object: </a>
```java
class Foo {
  public static int a() { return 1; }
}

Foo.a();
```
```kotlin
class Foo {
  companion object {
     fun a() : Int = 1
  }
}
Foo.a();
// Foo.Companion.a(); // access in java code
```


<details>
<summary>Static Methos Other Ways</summary>


> @JavaStatic annotation to use as normal static method inside java and kotlin all togather

```kotlin
class Foo {
  companion object {
    @JvmStatic
    fun a() : Int = 1;
  }
}
Foo.a() // from kotlin
Foo.a() // from java
```

> use "companion object name" signature other way of the @JavaStatic

```kotlin
class Foo {
  companion object Blah {
    fun a() : Int = 1;
  }
}
```

</details>

### <a name="primary-secondary-constructor"> Primary & secondary contructors: </a>
Note: if class has primary constructor, Secondary Constructor needs to be delegated to the primary constructor. The compiler select which constructor to use depending on the (number) supplied parameters.
```kotlin
class Constructors private constructor() {
    init {
        println("Init block")
    }

    constructor(i: Int): this() {
        println("Constructor $i")
    }
}


fun main() {
//     Constructors()
    Constructors(1)
}

// Init block
// Constructor 1
```

```kotlin
fun main() {    
    val myObj = Student("First", 15, 77)
    myObj.printMsg()
    val myObj2 = Student("Second", 72)
    myObj2.printMsgOnlyPrimaryConstructor()
    // omiting first param
    val myObj3 = Student(height = 72)
    myObj3.printMsgOnlyPrimaryConstructor()
}
 
class Student(var name: String="Default", height: Int) {
    val height = height
    var age: Int = 14
    constructor (value: String, age: Int, vb: Int): this(value, vb){
        this.age = age
    }
    fun printMsg(){
        println("Calling from Secondary Constructor => Name is $name. Age is $age. Height is $height");
    }
    fun printMsgOnlyPrimaryConstructor(){
        println("Primary Constructor => Name is $name, Height is ${this.height}");
    }
}

// Calling from Secondary Constructor => Name is First. Age is 15. Height is 77
// Primary Constructor => Name is Second, Height is 72
// Primary Constructor => Name is Default, Height is 72
``` 

### <a name="generic-variances"> Invariance, Covariance, Contravariance: </a>
- Invariance: A generic class is called invariant on the type parameter when for two different types A and B, Class<A> is neither a subtype nor a supertype of Class<B>

- Covariance (subtyping relation): A generic class is called covariant on the type parameter when the following holds: Class<A> is a subtype of Class<B>


- Contravariance (Supertype relation or reverse Covariance): Contravariance describes a relationship between two sets of types where they subtype in opposite directions (Supertype)
```kotlin
open class A
open class B : A()

class Box<in T> {
    private val items = mutableListOf<T>()
    fun deposit(item: T) = items.add(item)
}

//  B is a subtype of A, whereas…
// Box<B> is a supertype of Box<A>
// This happened because of the variance annotation "in"
// Box<A> is applicable anywhere that the code expects Box<B>
```
### <a name="generics-in-out"> Generics Variance (in/out) or variance annotation: </a>
 - out: When type parameter is to only returned (produced/out) from members of Source<T>, and never consumed.
```kotlin
 interface Source<out T> {
    fun nextT(): T
}

fun demo(strs: Source<String>) {
    val objects: Source<Any> = strs // This is OK, since T is an out-parameter
    // ...
}

fun copy(from: Array<out Any>, to: Array<Any>) { ... }
// here the "from" parameter needs to be returned


// For Foo<out T : TUpper>, where T is a covariant type parameter with the upper bound TUpper.
```

 - in: It makes a type parameter contravariant, meaning it can only be consumed(in) and never produced. Also Array<in String> corresponds to Java's Array<? super String>
```kotlin

interface Comparable<in T> {
    operator fun compareTo(other: T): Int
}

fun demo(x: Comparable<Number>) {
    x.compareTo(1.0) // 1.0 has type Double, which is a subtype of Number
    // Thus, you can assign x to a variable of type Comparable<Double>
    val y: Comparable<Double> = x // OK!
}

// can pass an array of CharSequence or an array of Object to the fill() function
fun fill(dest: Array<in String>, value: String) { ... }
```

### <a name="star-projection"> Star Projection (Generics): </a>
- Function<*, String> means Function<in Nothing, String>.
- Function<Int, *> means Function<Int, out Any?>.
- Function<*, *> means Function<in Nothing, out Any?>.
https://typealias.com/guides/star-projections-and-how-they-work/

### <a name="more-on-delegated-properties"> Delegated Properties (some-more):

- Lazy property: 
lazy() takes a lambda and returns an instance of Lazy<T>, which can serve as a delegate for implementing a lazy property. The first call to get() executes the lambda passed to lazy() and remembers the result. Subsequent calls to get() simply return the remembered result.
```kotlin
fun main() {
    println(lazyValue) // first call will print "Hello" and "Again"
    println(lazyValue) // second call will print only remembered get value which is -> "Again" 
}

// val will make it immutable. for var, custom setValue needs to be created.
val lazyValue: String by lazy {
    println("Hello")
    "Again"
}

// Print These
// Hello
// Again
// Again
```
If we call setter, it will call only the remembered value (getValu()), not anythig else, "println("Hello")" in this case
Also see Scoped Functions at https://kotlinlang.org/docs/scope-functions.html
```kotlin
fun main() {
    println(lazyValue)
    println(lazyValue)

    println()

    lazyValue = "Something"
    println(lazyValue) // will only print "Something" as remembered (getValue())
}

var lazyValue: String = run {
    println("Hello") // this will be called only once
    "Again"
}

// Print These
// Hello
// Again
// Again

// Something
```

- Delegates.observable(<initial-value>) { prop, old, new -> .......}
```kotlin
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
```

### Scope Functions (let, run, with, apply, and also):
The purpose of Scope Functions are to execute a block of code within the context of an object. It do not introduce any new technical capabilities, but make the code more concise and readable.

Docs: https://kotlinlang.org/docs/scope-functions.html

```kotlin
data class Person(var name: String, var age: Int, var city: String) {
    fun moveTo(newCity: String) { city = newCity }
    fun incrementAge() { age++ }
}

fun main() {
	// using Scope Function "let"
    Person("Alice", 20, "Amsterdam").let {
        println(it)
        it.moveTo("London")
        it.incrementAge()
        println(it)
    }
    
    println()
    
    // without Scope Function "let"
    val alice = Person("John", 21, "Canada")
    println(alice)
    alice.moveTo("Tokyo")
    alice.incrementAge()
    println(alice)
}
```
> Scope Functions Mini Map:

|Function|Object reference|Return value|Is extension function|
|---|---|---|---|
|`let`|`it`|Lambda result|Yes|
|`run`|`this`|Lambda result|Yes|
|`run`|-|Lambda result|No: called without the context object|
|`with`|`this`|Lambda result|No: takes the context object as an argument.|
|`apply`|`this`|Context object|Yes|
|`also`|`it`|Context object|Yes|

> short guide for choosing scope functions:

* Executing a lambda on non-null objects: `let`
* Introducing an expression as a variable in local scope: `let`
* Object configuration: `apply`
* Object configuration and computing the result: `run`
* Running statements where an expression is required: non-extension `run`
* Additional effects: `also`
* Grouping function calls on an object: `with`

- let:
```kt
package ScopeFunctions

fun main() {

    val numbers = mutableListOf("one", "two", "three", "four", "five")

    // again some map filter
    println(numbers.map{it.length}) // [3, 3, 5, 4, 4]
    println(numbers.map{it.length}.filter{it > 3}) // [5, 4, 4]
    println(numbers.filter{it != "five"}) // [one, two, three, four]
    println()

    // without let scope function
    val resultList = numbers.map { it.length }.filter { it > 3 }
    println(resultList)
    println()

    // with let scope funciton
    numbers.map { it.length }.filter { it > 3 }.let {
        println(it) // [5, 4, 4]
        println(it.filter{it != 5}) // [4, 4]
        println(it.filter{it != 4}) // [5]
        println("something else") // something else
        // and more function calls if needed
    }

    // If the code block contains a single function invocation, method reference (::) can be used instead of the lambda
    numbers.map { it.length }.filter { it > 3 }.let(::println)

    // null probability and safe-call operator "?" with "let"
    fun processNonNullString(str: String) {}
    val str: String? = "Hello"
    //processNonNullString(str)       // compilation error: str can be null
    val length = str?.let {
        println("let() called on $it") // let() called on Hello
        processNonNullString(it)    // OK: 'it' is not null inside '?.let { }'
        println(it.length) // 5
    }
}
```
- with: used for grouping function call on an objects. with's return type is same as lambda.
```kotlin
fun main() {

    val numbers = mutableListOf("one", "two", "three")
    val returnedValue = with(numbers) {
        println("'with' is called with argument $this") // 'with' is called with argument [one, two, three]
        println("It contains $size elements") // It contains 3 elements
    }
    // storing/returning calculated value inside helper object "val firstAndLast"
    val firstAndLast = with(numbers) {
        "This string will be returned : " +
        "The first element is ${first()}," +
                " the last element is ${this.last()}"
    }
    println(firstAndLast) // This string will be returned : The first element is one, the last element is three
}
```

- run: run does the same as with but invokes as let - as an extension function of the context object (object.run{}). run can also be used without extension function.
```kotlin
class MultiportService(var url: String, var port: Int) {
    fun prepareRequest(): String = "Default request"
    fun query(request: String): String = "Result for query '$request'"
}

fun main() {

    val service = MultiportService("https://example.com", 80)

    val result = service.run {
        port = 8080 // also "this.port = 8080"
        query(prepareRequest() + " to port $port")
    }
    
    // the same code written with let() function:
    val letResult = service.let {
        it.port = 8080
        it.query(it.prepareRequest() + " to port ${it.port}")
    }

    println(result)
    println(letResult)
}
```

run without extension function:
```kotlin

```

### <a name="kotlinsequence"></a> Sequences in Kotlin:
```kotlin
fun main() {
	
    // basic sequence
    val numbers = listOf("one", "two", "three", "four")
    val numbersSequence = numbers.asSequence()
    println(numbersSequence.toList()) // [one, two, three, four]
    
    // infinite sequence
    // if not blocked by else, sequence is infinite
    val oddNumbers = generateSequence(1) { it + 2 } // `it` is the previous element
    val s5 = oddNumbers.take(5).toList()
    println(oddNumbers.take(5).toList()) // [1, 3, 5, 7, 9]
    println(s5.count()) // 5
    //println(oddNumbers.count()) // error: the sequence is infinite
    
    //finite sequence
    val oddNumbersLessThan10 = generateSequence(1) { if (it < 8) it + 2 else null }
    println(oddNumbersLessThan10.count()) // 5
    println(oddNumbersLessThan10) // kotlin.sequences.GeneratorSequence@.......

}
```

- apply: The common case for apply is the object configuration. Such calls can be read as “apply the following assignments to the object.” The context object is available as a receiver (this). The return value is the configuired object itself.
```kotlin
data class Person(var name: String, var age: Int = 0, var city: String = "")

fun main() {
    val adam = Person("Adam").apply {
        age = 32
        city = "London"        
    }
    println(adam)
}
```

- also: Reat it as “and also do the following with the object.” The context object is available as an argument (it). The return value is the object itself.
```kotlin
fun main(){
    val numbers = mutableListOf("one", "two", "three")
    numbers
        .also {
            println("The list elements before adding new one: $it")
            it.add("four") // add() will return boolean, but "also" will return the object
        }.also {
            println("After adding the number object is = $it")
        }
    // The list elements before adding new one: [one, two, three]
    // After adding the number object is = [one, two, three, four]
}
```

- takeIf and takeUnless: These functions provide embed checks of the object state in call chains. The object is available as a lambda argument (it).
* If matched with the predicate, takeIf will return the object but takeUnless will return null
* If predicate don't match, takeIf will return Null but takeUnless will return the Object
```kotlin
import kotlin.random.*

fun main() {
    
    // When called on an object with a predicate provided
    // If matched with the predicate, takeIf will return the object but takeUnless will return null
    // If predicate don't match, takeIf will return Null but takeUnless will return the Object
    
    val number = Random.nextInt(100)
    println("The number is : $number")
	
    // takeIf returns this object if it matches the predicate. Otherwise, it returns null. So, takeIf is a filtering function for a single object.
    val evenOrNull = number.takeIf { it % 2 == 0 }
    
    // takeUnless returns the object if it doesn't match the predicate and null if it does. The object is available as a lambda argument (it).
    val oddOrNull = number.takeUnless { it % 2 == 0 }
    
    
    println("even: $evenOrNull, odd: $oddOrNull")

}
```
### <a name="regexsequence"></a> Regex Sequence:
```kotlin
fun main() {

    val hexNumberRegex = run {
        val digits = "0-9"
        val hexDigits = "A-Fa-f"
        val xToz = "X-Zx-z"
        val sign = "+-"

        // Regex("[$sign]?[$digits$hexDigits]+")
        // Regex("[+-]?[0-9A-Fa-f]+") // same
        Regex("[$sign]?[$digits$hexDigits$xToz]+")
    }

    for (match in hexNumberRegex.findAll("+123 -FFFF !%*& 88 XYZ")) {
        println(match.value)
    }

    // Regex().findAll("$string") will return kotlin.sequences.GeneratorSequence of all the matches starting from position 0 by default
    println(hexNumberRegex.findAll("+123 -FFFF !%*& 88 XYZ")) // kotlin.sequences.GeneratorSequence@.......
    println(hexNumberRegex.findAll("+123 -FFFF !%*& 88 XYZ").toList().map {it.value}) // [+123, -FFFF, 88, XYZ]
    println(hexNumberRegex.findAll("+123 -FFFF !%*& 88 XYZ").last().value) // XYZ
}
```

### Tasks:
- complete delegated properties
- complete scope function
- more on scope function and docs link
- more on regex and docs link