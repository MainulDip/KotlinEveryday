## Kotlin Abstract Class
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


Object declarations: Singleton Pattern
```kt
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


### Data Class
Data Classes' main purpose is to hold data and or data structure.
[Docs Data Class](https://kotlinlang.org/docs/data-classes.html)
> Signature: data class User(val name: String, val age: Int)

> automatically derives equals()/hashCode(), toString(), componentN() and copy() functions

Requirments
 .at least one parameter in primary constructor
 .All primary constructor parameters need to be marked as val or var
 .Data classes cannot be abstract, open, sealed, or inner

### Sealed Class
sealed class is abstract by itself, it cannot be instantiated directly and can have abstract members. It can have one of two visibilities: protected (by default) or private.

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

### Enum Class
[Implementation Play](./OOPTour/src/main/kotlin/dataSealedEnumClasses/emumclass.kt)
[Offficial Docs](https://kotlinlang.org/docs/enum-classes.html#anonymous-classes)
```kt
fun main(){
    println(Color.RED.rgb);
}

enum class Color(val rgb: String) {
    RED("0xFF0000"),
    GREEN("0x00FF00"),
    BLUE("0x0000FF")
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
    }; // separate the constant definitions from the member definitions with a semicolon
    
    // make it abstract first or if implementing from another interface then override implementation first then override again inside members (try to keep the first override meaningful)
    override fun applyAsInt(t: Int, u: Int) = apply(t,u)
}
```
### Inline Class