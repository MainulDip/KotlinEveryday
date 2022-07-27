### Module, Package and Visibility Modifires:
Module: It's the top-most level like App. More specifically a module is a set of Kotlin files compiled together, like an IntelliJ IDEA module or a maven project or a gradle source set. See (Docs)[https://kotlinlang.org/docs/visibility-modifiers.html#modules]

Packages: collect related classes, functions, and correspond roughly to directories. They allow to refer to classes (and top-level functions and fields) in the same package directly, while all other classes need to be imported, or their fully-qualified names (package.package…class) used. They're set using the package directive at the top of each file.

Visibility Modifires:
```kt
// file name: example.kt
package foo

private fun foo() { ... } // visible inside example.kt only. For class member, private means that the member is visible inside this class only (including all its members)

protected fun baz() {...} // like private modifires with subclass access. 

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
```
Docs: https://kotlinlang.org/docs/visibility-modifiers.html


### Kotlin Abstract Class
Docs: https://kotlinlang.org/docs/classes.html#abstract-classes
```kt
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


### Object | annonymous class, declarations: Singleton Pattern
Object expressions create objects of anonymous classes. anonymous classes are also called anonymous objects because they are defined by an expression, not a name.
Docs: https://kotlinlang.org/docs/object-declarations.html
```kt
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

//Such objects can have supertypes

object DefaultListener : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) { ... }

    override fun mouseEntered(e: MouseEvent) { ... }
}
// overriding methods
```
### Companion objects (inside class): Java static method + some more
> If declared inside of a class, it can access its members / internals-of-the-class (such as a factory method) using only the class name as a qualifier, without instantiation like static method (But not exactly). 
```kt
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
Hit on https://kotlinlang.org/docs/inheritance.html
```kt
class Example // Implicitly inherits from Any, and Any has three methods: equals(), hashCode(), and toString()
```
> Classes are final by default, To make a class inheritable, mark it with the open keyword. 

```kt
open class Base(p: Int){}
class Derived(p: Int) : Base(p){}
```

### Interface
Interfaces in Kotlin can contain declarations of abstract methods, as well as method implementations, but interfaces cannot store a state, They can have properties, but these need to be abstract or provide accessor implementations, can implement one or more interfaces

Docs: https://kotlinlang.org/docs/interfaces.html

```kt
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


### Data Class (Modeling)
Data Classes' main purpose is to hold data and or data structure.
[Docs Data Class](https://kotlinlang.org/docs/data-classes.html)
> Signature: data class User(val name: String, val age: Int)

> automatically derives equals()/hashCode(), toString(), componentN() and copy() functions

Requirments
 .at least one parameter in primary constructor
 .All primary constructor parameters need to be marked as val or var
 .Data classes cannot be abstract, open, sealed, or inner

```kt
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
### Sealed Class
Unique to kotlin only, its kinda like enum with more feature (IDE suggession, Error ).sealed class is abstract by itself, it cannot be instantiated directly and can have abstract members. It can have one of two visibilities: protected (by default) or private.

> Sealed vs Enum
_ Sealed class can have <T> (Generic type) parameters, but not enum
_ Sealed Class With Complex Hierarchy (Multiple Nested class/objects) is recognised by IDE. But enum with abstract class is not recogniseable/predictable by IDE, as the IDE compiler cannot get the inheritance Hierarchy. Specially working with "when" block, IDE can generate all the posible options/branches of the sealed class. Its always handy
_ Sealed class can hold "Instance Specefic Data", not only singleton
Docs: https://kotlinlang.org/docs/sealed-classes.html

```kt
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

1. Define Hierarchy Using Nested Class, its easy to read
```kt
sealed class Gender {
    object Male: Gender()
    object Female: Gender()
}
```

2. Best first case could when block. Also use generic getter to receive IDE support at its best
```kt
val getGender: String = when(person.gender){
    is Person.Gender.Male -> "Male"
    is Person.Gender.Female -> "Female"
}

val <T> T.exhaustive : T
    get() = this
```

</details>

### Extension Function On Class/Object:
[Docs](https://kotlinlang.org/docs/extensions.html)
Can extend a class with new functionality without having to inherit from the class. There are also extension properties that let you define new properties for existing classes.

```kt
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

```kt
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

### Enum Class:
Each enum constant is an object. Enum constants are separated by commas.
[Implementation Play](./OOPTour/src/main/kotlin/dataSealedEnumClasses/emumclass.kt)
[Offficial Docs](https://kotlinlang.org/docs/enum-classes.html#anonymous-classes)
```kt
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
```kt
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
### Inline Class, Nested, Inners:
Inline classes are a subset of value-based classes. They don't have an identity and can only hold values. Provide predictable type into IDE.

Inline Class Docs: https://kotlinlang.org/docs/inline-classes.html
Nested+Inner Classes docs: https://kotlinlang.org/docs/nested-classes.html

```kt
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
```kt
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

### Generics In Out, Deligation, SAM (Single Abstract Method)
> Deligation : alternative way of implementation inheritance. Only interfaces can be delegated

NB: Here class Derived can implement an interface Base by delegating all of its public members to a specified object.
```kt
interface Base {
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override fun print() { print(x) }
}

class Derived(d: Base) : Base by d // all the methods of Base interface has been forworded to Derived class

fun main() {
    val b = BaseImpl(10)
    Derived(b).print()
}

//The by-clause in the supertype list for "Derived" indicates that d will be stored internally in objects of "Derived" and the compiler will generate all the methods of Base that forward to d
```

> SAM : Function "Single Abstruct Method"

```kt
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
A coroutine is an instance of suspendable computation. Coroutines can be thought of as light-weight threads, but there is a number of important differences that make their real-life usage very different from threads.


```kt
import kotlinx.coroutines.*


fun main() = runBlocking { // this: CoroutineScope
    launch { // launch is a coroutine builder. It launches a new coroutine concurrently with the rest of the code, which continues to work independently
        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
        println("World!") // prints after delay
    }
    println("Hello") // main coroutine continues while a previous one is delayed
}

// prints to 
// Hello
// World!
```

### delay() vs Thread.speep() :

Delay is a suspend function that won't block the thread, it will only suspend that coroutine for the amount of time, but Thread is free to go service a different coroutine.

Thread.sleep() will block the thread and remain blocked until seeping time in the coroutine are over before it can go service the execution of another coroutine. Hence its Blocking. See Example

Note: Suspending a thread means that thread will "wait" doing something else in the meantime if necessary. Blocking a thread means that thread will wait doing nothing no matter what.

Link: https://stackoverflow.com/questions/61345712/what-is-the-difference-between-delay-and-thread-sleep-in-kotlin
```kt
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

### Late-initialized (lateinit):
To handle non-null properties/var that will be provided lately (through dependency injection, or in the setup method of a unit test), lateinit modifier can be used. lateint can be used on var properties declared inside the body of a class (not in the primary constructor, and only when the property does not have a custom getter or setter), as well as for top-level properties and local variables. The type of the property or variable must be non-null, and it must not be a primitive type.
Docs : https://kotlinlang.org/docs/properties.html#checking-whether-a-lateinit-var-is-initialized

```kt
lateinit var subject: TestSubject
```