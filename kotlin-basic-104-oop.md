## Kotlin OOP Part 2 Overview:
Continued From oop-tour-kotlin.md. This markdow file provides mini docs for following topics:
- [Companio Object | Static Method (Java)](#companion-static)
- [Primary & secondary contructors](#primary-secondary-constructor)
- [Invariance, Covariance, Contravariance](#generic-variances)
- [Generics Variance (in/out) or variance annotation](#generics-in-out)
- [Star Projection (Generics/Foo<*>):](#star-projection).
- [Delegated Properties (some-more):](#more-on-delegated-properties)

### <a name="companion-static"></a> java/kotlin Static Method || companion object:
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
<summary>Static Methods Other Ways</summary>


> @JavaStatic annotation to use as normal static method inside java and kotlin all together

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

### <a name="primary-secondary-constructor"></a> Primary & secondary contructors:
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

### <a name="generic-variances"> Invariance, Covariance, Contravariance:</a>
- Invariance: A generic class is called invariant on the type parameter when it is expecting the exact type. Not `in T` or Not `out R`

- Covariance (subtyping relation): a generic class is called covariant on the type parameter when it accept the exact class or it's subtype. Denotes by `out T`

```kotlin
// Covariance example
open class Animal
class Dog : Animal()

class AcceptedAnimal<out T>
// accept the exact class or it's subtype

fun main() {
    var animal: AcceptedAnimal<Animal> = AcceptedAnimal()
    var dog: AcceptedAnimal<Dog> = AcceptedAnimal()

    // dog = animal // assignment fails as <out Dog> will not accept it's supertype    
    animal = dog; // works as <out Animal> accepts Animal's subclass
}
```

- Contravariance (Supertype relation or reverse Covariance): a generic class is called Contravariance on the type parameter when it accept the exact class or it's supertype. denotes by `in T`

```kotlin
// contravariance example `out T`
open class Animal
class Dog : Animal()

class AcceptedAnimal<in T>
// accept the exact class or it's supertype

fun main() {
     var animal: AcceptedAnimal<Animal> = AcceptedAnimal()
    var dog: AcceptedAnimal<Dog> = AcceptedAnimal()

	// animal = dog // assignment will not work as <in Animal> don't accept int's subclass
    dog = animal // works, as Dog's superclass is Animal
}
```
### <a name="generics-in-out"></a> Generics Variance (in/out) or variance annotation | Consumers `in` Producers `out`
 - out: When type parameter is to only returned (produced/out) from members of Source<T>, and never consumed. `Producers Out`
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

 - in: It makes a type parameter contravariant, meaning it can only be consumed(in) and never produced. Also Array<in String> corresponds to Java's Array<? super String>. `Consumers in`
```kotlin

interface Comparable<in T> {
    operator fun compareTo(other: T): Int
}

fun demo(x: Comparable<Number>) {
    x.compareTo(1.0) // 1.0 has type Double, which is a subtype of Number
    // Thus, you can assign x to a variable of type Comparable<Double>
    val y: Comparable<Double> = x // OK!
}

// can pass an array of CharSequence or an array of object to the fill() function
fun fill(dest: Array<in String>, value: String) { ... }
```

### <a name="star-projection"> Star Projection (Generics): </a>
- Function<*, String> means Function<in Nothing, String>.
- Function<Int, *> means Function<Int, out Any?>.
- Function<*, *> means Function<in Nothing, out Any?>.
https://typealias.com/guides/star-projections-and-how-they-work/

### <a name="more-on-delegated-properties"> Delegated Properties (some-more)

- Lazy property: 
lazy() takes a lambda and returns an instance of Lazy<T>, which can serve as a delegate for implementing a lazy property. The first call to get() executes the lambda passed to lazy() and remembers the result. Subsequent calls to get() simply return the remembered results
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
// Hello 1st
// Again 1st
// Again 2nd
```
If we call setter, it will call only the remembered value (getValu()), not anything else, "println("Hello")" in this case
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
signature :
```kotlin
inline fun <T> observable(
    initialValue: T,
    crossinline onChange: (property: KProperty<*>, oldValue: T, newValue: T) -> Unit
): ReadWriteProperty<Any?, T>
```
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

###  let:

```kotlin
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
Unlike collections, sequences don't contain elements, they produce them while iterating. Sequences offer the same functions as Iterable but implement another approach to multi-step collection processing.

sequences should be better for the performance, but in real world it doesn't differ much as https://stackoverflow.com/questions/75503587/when-to-use-sequence-over-list-in-kotlin
```kotlin
fun main() {
	
    // basic sequence
    val sequenceWithoutList = sequenceOf("four", "three", "two", "one")
    val numbers = listOf("one", "two", "three", "four")
    val numbersSequence = numbers.asSequence()
    println(numbersSequence.toList()) // [one, two, three, four]
    
    // infinite sequence
    // if not blocked by else null, sequence is infinite. take(int) mimics the else case
    val oddNumbers = generateSequence(1) { it + 2 } // `it` is the previous element
    val s5 = oddNumbers.take(5).toList()
    println(oddNumbers.take(5).toList()) // [1, 3, 5, 7, 9]
    println(s5.count()) // 5
    //println(oddNumbers.count()) // error: the sequence is infinite
    
    //finite sequence using `else`
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
    // returning list of matched elements from sequence
    println(hexNumberRegex.findAll("+123 -FFFF !%*& 88 XYZ").toList().map {it.value}) // [+123, -FFFF, 88, XYZ]
    println(hexNumberRegex.findAll("+123 -FFFF !%*& 88 XYZ").last().value) // XYZ
}
```

### Inline Functions (lambda arguments):
https://www.baeldung.com/kotlin/crossinline-vs-noinline
Inline functions in Kotlin help us to avoid extra memory allocations and unnecessary method invocations for each lambda expression.

Using higher-order functions imposes certain runtime penalties: each function is an object, and it captures a closure. A closure is a scope of variables that can be accessed in the body of the function. Memory allocations (both for function objects and classes) and virtual calls introduce runtime overhead.

But it appears that in many cases this kind of overhead can be eliminated by inlining the lambda expressions.

```kotlin
// makes all lambda parameters inline
inline fun <T> lock(lock: Lock, body: () -> T): T { ... }

// makes only first lambda inline
inline fun foo(inlined: () -> Unit, noinline notInlined: () -> Unit) { ... }
```

Non-inline vs Inline in JVM compiler
```kotlin
fun higherfunc( str : String, funCall : (String) -> Unit) {
    funCall(str)
}

fun main(args: Array<String>) {
    higherfunc("Testing effect of non-inline and inline functions compiled JVM code"){
        println(it)
    }
}

// To Java Compiled
public final class InlineFunctionsBasicKt {
   public static final void higherfunc(@NotNull String str, @NotNull Function1 funCall) {
      Intrinsics.checkNotNullParameter(str, "str");
      Intrinsics.checkNotNullParameter(funCall, "funCall");
      funCall.invoke(str);
   }

   public static final void main(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      higherfunc("Testing effect of non-inline and inline functions compiled JVM code", (Function1)null.INSTANCE);

      // Note: Here Compiler is allocating more mamory by creating function's object, closure class and virtual calls behind the scene. But For simple cases we just need the output/return of that higherOrderFunction call only. Which is done in kotlin by adding inline before the function declaration.
   }
}

// making kotlin inline function
inline fun higherfunc( str : String, funCall : (String) -> Unit) {
    funCall(str)
}

// Inline Function To Compiled Java
public final class InlineFunctionsBasicKt {
   public static final void higherfunc(@NotNull String str, @NotNull Function1 funCall) {
      int $i$f$higherfunc = 0;
      Intrinsics.checkNotNullParameter(str, "str");
      Intrinsics.checkNotNullParameter(funCall, "funCall");
      funCall.invoke(str);
   }

   public static final void main(@NotNull String[] args) {
      Intrinsics.checkNotNullParameter(args, "args");
      String str$iv = "Testing effect of non-inline and inline functions compiled JVM code";
      int $i$f$higherfunc = false;
      int var4 = false;
      System.out.println(str$iv);

      // Note: After making kotlin inline function, the compiler is just calculating the output/return of that function on the fly without allocating function's objectc, closour classes and virtual calls in behind. For simple task it's more optimized.
   }
}
```

- crossinline, return and blocking return statement:
If function is declared inline, the parameterised lambda blocks are allowed to provide "return" statement. But if crossinline is declared before the parameter of the lambda functions, it will behave like normal lambda block where "return" is not normally allowed.

with crossinline keyword, we are telling the compiler, "give me an error, if I accidentally use a non-local return inside the nested functions or local objects"

Also, When the lambda parameter in an inline function is passed to another non-inline function context like this, we can’t use non-local returns.

```kotlin
inline fun foos(crossinline g: () -> Unit) {
    bar { g() } // crossinlie or noinline is necessary if inline functions callback parameter is passed inside non-inline function's callback
}

fun bar(f: () -> Unit) {
    f()
}

fun main(){
    foos {
        println("Simple Cross-inline")
//         return // non-local return is not allowed
    }
}
```
docs: https://kotlinlang.org/docs/inline-functions.html

- Reified type parameters:
To access a type passed as a parameter, instade of using reflection to check whether a node has a certain type, simply a type to this function can be passed by declaring "<reified T>".

```kotlin
inline fun <reified T> functionName(): T? {
    .......
    return p as T?
}
```

In kotlin, generics are erased in the runtime, so no "Type" is available otherthan specific single type. By using inline function with reified, the type is passed to functions body for accessing class's methods/properties.

```kotlin
inline fun <reified T> simpleTestReified(a: Any) {
    println()
    if (a is T) println("\"$a\" = \"${a::class}\" and T = \"${T::class}\" are same type")
}

// IDE error: Cannot check for instance of erased type: T -> in the if (a is T) || Will suggest to make inline reified
//fun <T : String> simpleTestReifiedS(a: Any) {
//    println()
//    if (a is T) println("\"$a\" = \"${a::class}\" and T = \"${T::class}\" are same type")
//}

fun main() {
    simpleTestReified<String>("This is String")
}
```


<details>
<summary>More on reified: Practical Example</summary>

Abstract: Here, we will call a function to only print some info of a specific type from a list of different type, based on the supplied type.

```kotlin
sealed class Mammal(val name: String)

data class Sloth(
    val slothName: String,
    val isTwoFingered: Boolean,
    var slothWeight: Int
) : Mammal(slothName)

data class Manatee(val manateeName: String) : Mammal(manateeName)

data class Panda(val pandaName: String) : Mammal(pandaName)

fun Mammal.knownSpeciesCount(): Int {
    return when (this) {
        is Sloth -> 6
        is Panda -> 2
        is Manatee -> 3
    }
}

// reified
// here "<reified T : Mammal>" is only necessary for the "list.filterIsInstance<T>()...." block to filter based on provided type
inline fun <reified T : Mammal> printAnimalResultFiltered(
    list: List<Mammal>,
    factCheck: Mammal.() -> Int
): List<Mammal> {
    if (list.isNotEmpty()) {
        list.filterIsInstance<T>() // To filter based on supplied type, we need to apply Generics T with <reified T : Mammal>
            .forEach {
                println("${it.javaClass.name} - ${it.factCheck()}")
            }
    }
    return list
}

fun main() {
    val crewCrewCrew = listOf(
        Sloth("Jerry", false, 15),
        Panda("Tegan"),
        Manatee("Manny")
    )

    println("\nSpecies count with list as param:")
    println("First: ")
    printAnimalResultFiltered<Panda>(crewCrewCrew, Mammal::knownSpeciesCount)
    println("Second: ")
    printAnimalResultFiltered<Sloth>(crewCrewCrew, Mammal::knownSpeciesCount)
}
```

NB: Because of the inline and reified declaration, we can access/filter the supplied class in the function's body using "list.filterIsInstance<T>()"

If we don't declare the function inline with reified, we cannot access generic "type" information from the function body. Because normally, all the generic "type" information is erased by erasure after compilation, so in runtime do don't have the generic types, only specific type. But Kotlin provide a tricky solution as "inline reified", which help us get the generic type information in the runtime.

</details>


### De-Structuring parameter using `()`:
```kotlin
// destructuring
data class DUser(val name: String, val age: Int, val group: String)

var dUserList: List<DUser> = mutableListOf(DUser("First User", 21, "a"), DUser("Second User", 22, "b"),DUser("Third User", 23, "c"), DUser("First User", 21, "b"))

fun List<DUser>.arrange(): List<DUser> =
    // groupBy returns Map<Key, List<DUser>>, that's why on map, $it de-structured to (key, list)
    groupBy {
        it.name
    }.map { (key, list) -> DUser(key, list[0].age, list[0].group) }

fun main() {
    val users = dUserList.arrange()
    println("After Arranging using groupBy \nUsers : $users \nAnd DUser Count is : ${users.size} where original dUserList was ${dUserList.size}")
}

//After Arranging using groupBy
//Users : [DUser(name=First User, age=21, group=a), DUser(name=Second User, age=22, group=b), DUser(name=Third User, age=23, group=c)]
//And DUser Count is : 3 where original dUserList was 4
```

### Tasks:
- complete delegated properties.
- complete scope function
- more on scope function and docs link
- more on regex and docs link
- inline functions and check the JVMs compiled code
- more on kotlin reified : https://medium.com/kotlin-thursdays/introduction-to-kotlin-generics-reified-generic-parameters-7643f53ba513