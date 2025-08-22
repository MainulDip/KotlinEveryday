### Module, Package and Visibility Modifiers: </a>
Module: It's the top-most level like App. More specifically a module is a set of Kotlin files compiled together, like an IntelliJ IDEA module or a maven project or a gradle source set. See (Docs)[https://kotlinlang.org/docs/visibility-modifiers.html#modules].

Packages: These are collection of related classes, functions, and correspond roughly to directories. They allow to refer to classes (and top-level functions and fields) in the same package directly, while all other classes need to be imported or their fully-qualified names (package.package…class) used. They're set using the package directive at the top of each file

Visibility Modifiers:
```kotlin
// file name: example.kt
package com.websolverpro.foo

private fun foo() { ... } // visible inside example.kt only. For class member, private means that the member is visible inside this class only (including all its members)

protected fun baz() {...} // like private modifiers with subclass access. 

public var bar: Int = 5 // property is visible everywhere
    private set         // setter is visible only in example.kt

internal val baz = 6    // visible inside the same module

open class Outer {
    private val a = 1
    protected open val b = 2
    internal open val c = 3
    val d = 4  // public by default

    protected class Nested {
        public val e: Int = 5
    }
}

class Subclass : Outer() {
    // a is not visible
    // b, c and d are visible
    // Nested and e are visible

    override val b = 5   // 'b' is protected
    override val c = 7   // 'c' is internal
}

class Unrelated(o: Outer) {
    // o.a, o.b are not visible
    // o.c and o.d are visible (same module)
    // Outer.Nested is not visible, and Nested::e is not visible either
}
// there is also an "actual" keyowrd like -> public actual fun() ...., actual denotes a platform-specific implementation in multiplatform projects.
```
Docs: https://kotlinlang.org/docs/visibility-modifiers.html

### `val/var` and `no-modifier` in Constructor :
If val/var is specified in constructor parameter, in background it also declares property inside the class. If not it is simply a parameter passed to the primary constructor, where the parameters can be accessed within the init block or to initialize other properties...
```kotlin
class User(val id: Long, email: String) {
    val hasEmail = email.isNotBlank()    //email can be accessed here
    init {
        //email can be accessed here
    }

    fun getEmail(){
        //email can't be accessed here
    }
}
```

### `Any`, `Unit`, `Nothing`:
- Any: default superclass of all the classes and has 3 functions: equals, hashCode and toString.

- Unit: Unit class is a singleton class, we can't extend or even create an object of it. Same as java void type. The superclass of Unit is Any.

- Nothing:  Nothing is non-open (final class) which can't be extended and its constructor is also private, so we can't create the object form it. This is usually used to represent the return type of function which will always throw an exception. Also superclass of Nothing is Any.


```kotlin
fun main() {
    nothing(7,4)
}

fun nothing(x: Int, y: Int): Nothing {
    val exception1 = Exception("For $x : throw exception is the only Nothing Type")
    val exception2 = Exception("For $y : throw exception is the only Nothing Type")
    if (x > y) throw exception1 else throw exception2
}
```

### Kotlin Abstract Class:
Docs: https://kotlinlang.org/docs/classes.html#abstract-classes
```kotlin
abstract class Polygon {
    abstract fun draw()
}

class Rectangle : Polygon() {
    override fun draw() {
        // draw the rectangle
    }
}

//You can override a non-abstract open member with an abstract one.

open class Polygon {
    open fun draw() {
        // some default polygon drawing method
    }
}

abstract class WildShape : Polygon() {
    // Classes that inherit WildShape need to provide their own
    // draw method instead of using the default on Polygon
    abstract override fun draw()
}
```


### `object` | anonymous class, declarations | Object is Singleton Pattern equivalent in kotlin:
Object expressions create objects of anonymous classes. anonymous classes are also called anonymous objects because they are defined by an expression, not a name.
Docs: https://kotlinlang.org/docs/object-declarations.html
```kotlin
fun main() {
    val helloWorld = object {
        val hello = "Hello"
        val world = "World"
        // object expressions extend Any, so `override` is required on `toString()`
        override fun toString() = "$hello $world"
    }
    print(helloWorld)
}

/**
*
*/
object DataProviderManager {
    fun registerDataProvider(provider: DataProvider) {}
}
//To refer to the object, use its name directly, no instantiation required voila
DataProviderManager.registerDataProvider(...)

//Such objects can have supertype

object DefaultListener : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) { ... }

    override fun mouseEntered(e: MouseEvent) { ... }
}
// overriding methods
```

### SAM Interface Implementation using `object`:
```kotlin
interface Source<out T> {
    fun nextT(): T
}

fun demo(strs: Source<String>): Source<Any> {
    return strs
}

fun main(){
    val d = demo(object : Source<String> {
        override fun nextT(): String {
            return "hello"
        }
    })

    println(d.nextT())
}
```
### `Companion object` (inside class): Java static method:

> If declared inside of a class, it can access its members / internals-of-the-class (such as a factory method) using only the class name as a qualifier, without instantiation like static method (But not exactly). 

Note:
- Object anonymous expressions (`companion object: {....}`) are executed (and initialized) immediately, where they are used.

- Object named declarations (`companion object ObjName {...}`) are initialized lazily, when accessed for the first time.

- A companion object is initialized when the corresponding class is loaded (resolved) that matches the semantics of a Java static initializer.

```kotlin
class MyClass {
    companion object Factory {
        fun create(): MyClass = MyClass()
    }
}

val f = MyClass.Factory()

class MyClass1 {
    companion object Named { }
}

val x = MyClass1

class MyClass2 {
    companion object { }
}

val y = MyClass2


// unlike static method in java, companion object can implement interfaces (unless @JvmStatic annotation)

interface Factory<T> {
    fun create(): T
}

class MyClass {
    companion object : Factory<MyClass> {
        override fun create(): MyClass = MyClass()
    }
}

val f: Factory<MyClass> = MyClass
```

### Inheritance
Classes are final by default, To make a class inheritable, mark it with the `open` keyword. All classes inherit from `Any`
```kotlin
// All classes Implicitly inherits from `Any`, and `Any` has three methods: equals(), hashCode(), and toString()
class Example
open class Base(p: Int){}
class Derived(p: Int) : Base(p){}
```

### `interface` | cannot instantiate directly | can't hold state | no-constructor | SAM for Functional Interfaces:
Interfaces in Kotlin can contain declarations of abstract methods, as well as method implementations, but interfaces cannot store a state, 
They can have properties, but these need to be abstract or provide accessor implementations. Interface can implement one or more other interfaces. 

Interface in Kotlin doesn't have constructor (java has and when called from kotlin it is converted using Single Abstract Method). Sometimes we need to define an anonymous object implementing the interface.
Docs: https://kotlinlang.org/docs/interfaces.html

```kotlin
fun main(){
    println(Child().prop)
    println(Child().foo())
    println(Child().someval())
    println(Child().somevalSec())
    println(Child().somevalfoo())
}

interface MyInterface {
    val prop: Int // abstract

    val propertyWithImplementation: String
        get() = "foo"
    // val propertyWithImplementation: String = "Some Val" // not allowed

    fun foo() {
        print(prop)
    }

    fun someval(): String {
        return "Some Value"
    }

    fun somevalSec(): Int {
        return prop
    }

    fun somevalfoo(): String {
        return propertyWithImplementation
    }
}

class Child : MyInterface {
    override val prop: Int = 29
}
```


### `data class` (Data Modeling | Entity) | one `val/var` is required:
Data Classes' main purpose is to represent data or data structure.
- Signature: `data class User(val name: String, val age: Int)`
- Automatically derives equals()/hashCode(), toString(), componentN() and copy() functions

Requirements
 - at least one parameter in primary constructor
 - All primary constructor parameters need to be marked as val or var
 - Data classes cannot be abstract, open, sealed, or inner

```kotlin
fun main() {
    val b = Bank("777", 12.34, 7)

    println("Bank Data is \$b = $b")
}

data class Bank (
    val accountNumber: String,
    var trust: Double,
    val transactionFee: Int
){
    init {
        trust = trust.toDouble()
        println("Initializing Bank")
    }
}
```
### Sealed Class and Sealed Interface:
Its kinda like enum with more feature (IDE suggession, Error ).
Saled class is abstract by itself, it cannot be instantiated directly and can have abstract members. It can have one of two visibilities: protected (by default) or private

> Sealed vs Enum
- Sealed class can have <T> (Generic type) parameters, but not enum
- Sealed Class With Complex Hierarchy (Multiple Nested class/objects) is recognised by IDE. But enum with abstract class is not recogniseable/predictable by IDE, as the IDE compiler cannot get the inheritance Hierarchy. Specially working with "when" block, IDE can generate all the posible options/branches of the sealed class. Its always handy
- Sealed class can hold "Instance Specefic Data", not only singleton
Docs: https://kotlinlang.org/docs/sealed-classes.html

```kotlin
sealed interface Error {}

sealed class IOError(): Error { // direct subclass as it is also a sealed class
    constructor() { /*...*/ } // protected by default
    private constructor(description: String): this() { /*...*/ } // private is OK
    // public constructor(code: Int): this() {} // Error: public and internal are not allowed

}

class FileReadError(val file: File): IOError() // indirect subclass
class DatabaseError(val source: DataSource): IOError()

object RuntimeError : Error
```
> Direct subclasses of sealed classes and interfaces must be declared in the same package. 

<details>
<summary>Sealed Class Best Practice</summary>

1. Define Hierarchy () Using Nested Class, its easy to read, also 
```kotlin
sealed class Gender {
    object Male: Gender()
    object Female: Gender()
}
```

2. Best first case could when block. Also use generic getter to receive IDE support at its best
```kotlin
/**
* without sealed class IDE will not auto suggest all the possibilities.
* adding extension `T.exhaustive` will help IDE to auto suggest
* `expression` -> when returned value is captured in a variable
* `statements` are not stored, rather do side effect/s (like modifying variable/s). 
* as a whole, most of the things are `statement`. Inside statement, there can be more `expressions` or `statements`
* here the when block's returned value is stored in a variable, thats why its an expression
*/
val getGender: String = when(person.gender){
    is Person.Gender.Male -> "Male"
    is Person.Gender.Female -> "Female"
}.exhaustive

val <T> T.exhaustive : T
    get() = this
```
3. sealed class with `when` block "expression" (returns) helps IDE to generate all the possible cases using "add remaining branches"
```kotlin
fun main(args: Array<String>) {

    val person = Person("Hello", Person.Gender.Male)

    /**
    * When calling as expression (not as statement)
    * a coed block is usually counted as an expression when it returns something
    * if used as expression, we don't need the exhaustive extension function for IDE support to cover every possible cases */

    val getGenderExpression = genderExpression(person.gender)
    println("getGenderExpression : $getGenderExpression")

}

/**
 * using when block with sealed class as an expression
 * use "add remaining branches" form IDE suggestion to generate all the possible cases */

fun genderExpression (gender: Person.Gender): String = when(gender) {
    Person.Gender.Female -> "Male"
    Person.Gender.Male -> "Female"
    // is Person.Gender.Male -> "Female" // "is" is implicit here
}


data class Person(val name: String, val gender: Gender ) {
    sealed class Gender {
        object Male: Gender()
        object Female: Gender()
    }
}
```

</details>

### Extension Function On Class/Object:
Can extend a class with new functionality without having to inherit from the class. 
There are also extension properties that let you define new properties for existing classes.

[Docs](https://kotlinlang.org/docs/extensions.html)

```kotlin
val list = mutableListOf(1, 2, 3)
list.swap(0, 2) // 'this' inside 'swap()' will hold the value of 'list'

fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // 'this' corresponds to the list
    this[index1] = this[index2]
    this[index2] = tmp
}

// generic is also possible
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // 'this' corresponds to the list
    this[index1] = this[index2]
    this[index2] = tmp
}
```

<details>
<summary>More on Extention function</summary>

```kotlin
fun main(){
    class Example {
        fun printFunctionType() { println("Class method") }
    }

    // this will be shadowed by the original declaration
    fun Example.printFunctionType() { println("Extension function") }

    fun Example.printFunctionType(v : String) { println("Extension function $v") }

    Example().printFunctionType() // Class method
    Example().printFunctionType("With Params") // Extension function With Params



    open class Shape
    class Rectangle: Shape()

    fun Shape.getName() = "Shape"
    fun Rectangle.getName() = "Rectangle"

    fun printClassName(s: Shape) {
        println(s.getName())
    }

    printClassName(Rectangle()) // Shape

    println(Rectangle().getName()) // Rectangle
}

```

</details>

### Enum Class | defines type-safe values:
Each enum constant is an object (an instance of the surrounding enum class). Enum constants are separated by commas.
[Implementation Play](./OOPTour/src/main/kotlin/dataSealedEnumClasses/emumclass.kt)
[Offficial Docs](https://kotlinlang.org/docs/enum-classes.html#anonymous-classes).
```kotlin
/**
* as each enum constants is an instance of the Enum Itself,
* the constructor of the Enum is passed down to those member enum constants. those constants cannot be changed once defined from outside
 * Note: the `constant`s are immutable
 * and the purpose of enum class is to define a collection of type-safe (safe to type) values
*/
enum class Color(val rgb: String) {
    RED("0xFF0000"),
    GREEN("0x00FF00"),
    BLUE("0x0000FF")
}


fun main() {
    println(Color.RED) // Print -> RED
    println(Color.RED.rgb) // Print -> 0xFF0000
    println(Color.valueOf("RED")) // Print -> RED
    println(Color.valueOf("RED").rgb) // Print -> 0xFF0000
    for (item in Color.values()){
        println(item.rgb)
    }
//  print   0xFF0000
//  print   0x00FF00
//  print   0x0000FF
}
```

Implementing interfaces in emum classes
```kotlin
import java.util.function.BinaryOperator
import java.util.function.IntBinaryOperator

fun main() {
    val a = 13
    val b = 31
    for (f in IntArithmetics.values()) {
        println("$f($a, $b) = ${f.apply(a, b)}")
    }
}

enum class IntArithmetics : BinaryOperator<Int>, IntBinaryOperator {
    PLUS {
        override fun apply(t: Int, u: Int): Int = t + u
    },
    TIMES {
        override fun apply(t: Int, u: Int): Int = t * u
    }; // separate the constant "property" definitions from the member "function" definitions with a semicolon
    
    // make it abstract first or if implementing from another interface then override implementation first then override again inside members (try to keep the first override meaningful)
    override fun applyAsInt(t: Int, u: Int) = apply(t,u)
}
```
* Enum ordinal and entries

```kotlin
/**
 * Enum ordinal is the Enum's constant's position on the Enum
 * As any enum constants are instances of the surrounding Enum class
 * to declare a method and call it by enum.constant.method() we need to make it abstract
 * then the method needs to be overridden
 */

enum class IntArithmetics  {
    PLUS { // PLUS is an anonymous enum Constant class (or anonymous class constant)
        override fun sth(t: Int, u: Int): Int = t + u
    },
    TIMES { // Same as PLUS, its an instances of the surrounding Enum class
        override fun sth(t: Int, u: Int): Int = t * u
    };

    abstract fun sth(t: Int, u: Int): Int
}

fun main() {
    println("${IntArithmetics.PLUS.ordinal}, ${IntArithmetics.TIMES.ordinal}") // 0, 1
    println(IntArithmetics.entries) // [PLUS, TIMES]

    val a = 13
    val b = 31

    println(IntArithmetics.PLUS.sth(a,b)) // 44
    println(IntArithmetics.TIMES.sth(a,b)) // 403

    for (f in IntArithmetics.entries) {
        println("$f($a, $b) = ${f.sth(a,b)}")
    }
    // PLUS(13, 31) = 44
    // TIMES(13, 31) = 403
}
```

### `inline` Class, `nested`, `inners`:
Inline classes are a subset of value-based classes. They don't have an identity and can only hold values. Provide predictable type into IDEs

Inline Class Docs : https://kotlinlang.org/docs/inline-classes.html.
Nested+Inner Classes docs: https://kotlinlang.org/docs/nested-classes.html

```kotlin
@JvmInline
value class Width(val width: Long)
@JvmInline
value class Height(val height: Long)

class Rect (private val w : Width, private val h: Height){
   fun printDim(){
       // println("w  h = $w  $h")
       // w  h = Width(width=100)  Height(height=70)

       println("w * h = ${w.hashCode() * h.hashCode()}")
       // w * h = 7000
   }
}

fun main() {
    val width = Width(100L)
    val height = Height(70L)

    val shape = Rect(width, height) // IDE will force setting correct Value
    // Rect(height, width) will throw an error because of explicitly using inline class as type
    shape.printDim()
}
```

> Nested and Inner Class
```kotlin
class Outer {
    private val bar: Int = 1
    class Nested {
        fun foo() = 2
        //fun baz() = bar // outer class properties not accessible in Nested Class
    }

    inner class Inner {
        fun foo() = bar // accessible in Inner Class
    }
}

fun main() {
    val n = Outer.Nested().foo() // == 2
    val i = Outer().Inner().foo() // == 1
}
```

### Delegation of `interface`: 
Delegation : alternative way of implementation inheritance. Only interfaces can be delegated

NB: Here class Derived can implement an interface Base by delegating all of its public members to a specified object.


Note: More specifically, the Derived class is implementing Base's public member print() from BaseImpl's implementation. It's not possible using just interface.

```kotlin
interface Base {
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override fun print() { print(x) }
}

class Derived(d: Base) : Base by d // all the methods of Base interface has been forwarded to Derived class

fun main() {
    val b = BaseImpl(10)
    Derived(b).print()
}

//The by-clause in the supertype list for "Derived" indicates that d will be stored internally in objects of "Derived" and the compiler will generate all the methods of Base that forward to d
```

### Delegated Property `val/var x: <T> by <T>`: </a>
These are properties that inherit getter and setter from another class/interface (Delegated Class) instead of it's own get() and set() method. The `by` keyword indicates that the property is controlled by the provided delegate instead of its own field (get(),set()).

Signature: `val/var <property name>: <Type> by <expression>`

The `get()` (and `set()`) that correspond to the property will be delegated to its (Class) getValue() and setValue() methods. Property delegates don’t have to implement an interface, but they have to provide a getValue() function (and setValue() for vars).......

```kotlin
import kotlin.reflect.KProperty

class Delegate {
    private var value: String? = null // lateinit will not work here, as we are calling the getValue first before setValue
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
    // var p: String by Delegate()
    var p by Delegate() // type inference
}

fun main(){
    var e = Example()
    println(e.p) // value = "Not Yet Set"
    e.p = "Hello World" // Hello World has been assigned to 'p' in delegation.Example@.......
    println(e.p) // value = "Hello World"
}
```
See more in action inside OOPTour's delegate package


* Concept of delegated property with DatabaseDelegate class

```kotlin
class DatabaseDelegate<in R, T>(readQuery: String, writeQuery: String, id: Any) : ReadWriteDelegate<R, T> {
    fun getValue(thisRef: R, property: KProperty<*>): T {
        return queryForValue(readQuery, mapOf("id" to id))
    }

    fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        update(writeQuery, mapOf("id" to id, "value" to value))
    }
}

class DatabaseUser(userId: String) {
    var name: String by DatabaseDelegate(
      "SELECT name FROM users WHERE userId = :id", 
      "UPDATE users SET name = :value WHERE userId = :id",
      userId)
    var email: String by DatabaseDelegate(
      "SELECT email FROM users WHERE userId = :id", 
      "UPDATE users SET email = :value WHERE userId = :id",
      userId)
}
```

### Generics In Out, SAM (Single Abstract Method):
> SAM : Function "Single Abstract Method"

```kotlin
fun interface IntPredicate {
   fun accept(i: Int): Boolean
}

val isEven = IntPredicate { it % 2 == 0 }

// Using non-SAM way by Creating an instance of a class
val isEvenNonSAM = object : IntPredicate {
   override fun accept(i: Int): Boolean {
       return i % 2 == 0
   }
}

fun main() {
   println("Is 7 even? - ${isEven.accept(7)}")
   println("Is 4 even? - ${isEvenNonSAM.accept(4)}")
}
```

### Coroutine, Suspend Functions (Asynchronous Tasks):
A coroutine is an instance of suspend-able computation. It is conceptually similar to a thread (virtual), in the sense that it takes a block of code to run that works concurrently with the rest of the code. However, a coroutine is not bound to any particular physical thread. It may suspend its execution in one thread and resume in another one.

```kotlin
// import kotlinx.coroutines.delay
// import kotlinx.coroutines.launch
// import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.*

fun main() {

    // runBlocking will block execution until delay suspend function is finished
    
    runBlocking { // this: CoroutineScope
        launch { // launch is a coroutine builder. It launches a new coroutine concurrently with the rest of the code, which continues to work independently
            delay(10L) // block launch-scope for 1 second (default time unit is ms). But doesn't block runBlockingScope
            println("World 1") // prints after delay
        }
        launch {
            delay(0L)
            println("World 2")
        }
        // delay(10L)
        println("Hello") // runs first as nothing is blocking it's way
    }

    // Hello
    // World 2
    // World 1

    // Second runBlocking will start executing after finishing First

    runBlocking {
        // "Hello 1" and 3rd launch function will print first, because 3rd launch has no delay (delay is a suspend function)
        // 1st and 2nd launch will be printed second as the both has same milliseconds of delay suspend function

        // 1st
        launch {
            delay(1000L)
            println("World 1")
        }

        // 2nd
        launch {
            delay(1000L)
            println("World 2")
        }

        // 3rd
        launch {
            delay(0L)
            println("World 3")
        }

        println("Hello 1")
    }

//    Hello
//    World!
//    Hello 1
//    World 3
//    World 1
//    World 2
}
```

See separate section for more: [coroutine-tour.md](./coroutine-tour.md)

### delay() vs Thread.sleep() : 

Delay is a suspend function that won't block the thread, it will only suspend that coroutine for the amount of time, but Thread is free to go service a different coroutine.

Thread.sleep() will block the thread and remain blocked until seeping time.

Note: Suspending a thread means that thread will "wait" doing something else in the meantime if necessary. Blocking a thread means that thread will wait doing nothing no matter what

Link: https://stackoverflow.com/questions/61345712/what-is-the-difference-between-delay-and-thread-sleep-in-kotlin
```kotlin
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

fun log(message: String) {
    println("[${Thread.currentThread().name}] : $message")
}

fun main() {
    runBlocking {
        val myThread = newSingleThreadContext("My Thread")

        launch(myThread) {
            (1..3).forEach {
                log("1st launch: $it")
                //delay(1000)
                Thread.sleep(1000)
            }
        }

        launch(myThread) {
            (1..3).forEach {
                log("2nd launch: $it")
                //delay(1000)
                Thread.sleep(1000)
            }
        }
    }
}

// Output with delay:

// [My Thread] : 1st launch: 1
// [My Thread] : 2nd launch: 1
// [My Thread] : 1st launch: 2
// [My Thread] : 2nd launch: 2
// [My Thread] : 1st launch: 3
// [My Thread] : 2nd launch: 3

// Output with Thread.sleep:

// [My Thread] : 1st launch: 1
// [My Thread] : 1st launch: 2
// [My Thread] : 1st launch: 3
// [My Thread] : 2nd launch: 1
// [My Thread] : 2nd launch: 2
// [My Thread] : 2nd launch: 3
```

### Late-initialized `lateinit var`: 
Used To handle non-null properties/var that will be provided lately (through dependency injection, or in the setup method of a unit test). As if we're telling the compiler to ignore null check and we're promising to populate the field before calling. So in runtime it will be fine

These are used on var properties declared inside class body as well as for top-level (outside class) properties and local variables. But not in the primary constructor, and only when the property does not have a custom getter or setter. 

The type of the property or variable must be non-null, and it must not be a primitive type.
Docs : https://kotlinlang.org/docs/properties.html#checking-whether-a-lateinit-var-is-initialized

```kotlin
lateinit var subject: TestSubject // value is computed only on first access later, not here when it is initialized
```

Note: The `lateinit` keyword is a promise that the code will initialize the variable before using it. If not, app will crash for null.

### property `get()`, `set(value)` & get's Backing `field`:
If custom get/set is defined, it will be called every time when accessing and setting respectively. Computed property can be implemented using `get`. If initial value exists, `field` is available as the current state.

`set` has access to the newly set value as parameter.

```kotlin
val initial = "Hello"
var x: String = initial // to access `field`, initial value is required 
	get() = field.uppercase()
	set(v){
      field = v.uppercase()
    }

fun main() {
    println(x)
    x = "World"
    println(x)
}

// single line getter
val fullName get() = "${this.fName} ${this.lName}" // type inferred

// making setter private, so it cannot be set from outside of the class
var sth: String = "abc"
    private set
```
Docs https://kotlinlang.org/docs/properties.html

### <a name="operator-function"> Operator functions:</a>
Operators are like +, -, /, * etc. Under the hood, expression a+b transform to a.plus(b) and same for other operators. To customize default behavior/functionality, we can override those using "operator fun <name>" signature.

```kotlin
fun main(args: Array<String>) {
    val cm1 = CustomMath(3, -8) // first CustomMath object
    val cm2 = CustomMath(4, 9) // second CustomMath object

    var sum = cm1.plus(cm2) // same as "cm1 + cm2"

    println("sum = (${sum.a}, ${sum.b})") // a and b are accessible as public modifier
}

class CustomMath(val a: Int, val b: Int) {

    // overloading plus "+" operator function
    operator fun plus(p: CustomMath) : CustomMath {
        println(a) // this is constructor's "a" value or first CustomMath object's "a" value // prints => 3
        println(p.a) // this is plus(cm2) or passing second CustomMath object // prints => 4
        return CustomMath(a + p.a, b + p.b) // returns a new CustomMath Object // prints => sum = (7, 1)
    }
}

// 3
// 4
// sum = (7, 1)
```
### indexing operator [] and get call
```kotlin
class MyClass {
    operator fun get(key: Int) = "method 1!"
    operator fun get(key: String) = "method 2!"
    fun get(key: Any) = "Anything Else!"
}

fun main(args: Array<String>) {
    val myClass = MyClass()
    println(myClass[23]) //"method 1!"
    println(myClass["23"]) //"method 2!"
    println(myClass.get(23)) //"method 1!"
    println(myClass.get(100L)) // Anything Else!
}
```

### Dig deeper into kotlin union types (using sealed class or else):