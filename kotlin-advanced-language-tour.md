## Kotlin Advanced Language Tour Overview
It's a personalized guide and quick tour of the day to day used kotlin language advanced/non-basic syntaxes and features. Like Kotlin DSL (Builders), Context and Extention functions, HigherOrder etc.

### Context and Extention functions
```kt
fun call(greet: String.(String) -> Unit) {
// greet("Hello", " Dolly") // "greet"'s 1st parameter is the context and the last is it's native parameter.
    "Hello".greet("Dolly") // can be also written this way
}

// define the function signature using lambda
call { name ->
    println("${this.toUpperCase()} $name") // Print => HELLO Dolly
}

// this is somewhat similar with Javascript's call(), apply() or bind() method
```

<details>
<summary>DSL Conversion</summary>

```kt
fun call(greet: String.() -> Unit) {
"Hello".greet()
}

call {
println("${this.toUpperCase()}") // Print => HELLO
}
```
</details>

<details>
<summary>This: Explecit/Implecit "this" call</summary>

```kt
class A { // implicit label @A
    inner class B { // implicit label @B
        fun Int.foo() { // implicit label @foo
            val a = this@A // A's this
            val b = this@B // B's this

            val c = this // foo()'s receiver, an Int
            val c1 = this@foo // foo()'s receiver, an Int

            val funLit = lambda@ fun String.() {
                val d = this // funLit's receiver
            }

            val funLit2 = { s: String ->
                // foo()'s receiver, since enclosing lambda expression
                // doesn't have any receiver
                val d1 = this
            }
        }
    }
}
```

Note: "this" can be ommited if call in a member function. But if there is a function in the outer scope, "this" necessery to call member function. Without "this" receiever top level function will be called.
```kt
fun printLine() { println("Top-level function") }

class A {
    fun printLine() { println("Member function") }

    fun invokePrintLine(omitThis: Boolean = false)  { 
        if (omitThis) printLine() // Top-level function
        else this.printLine() // Member function
    }
}

A().invokePrintLine() // Member function
A().invokePrintLine(omitThis = true) // Top-level function
```
</details>

### Kotlin Builders DSLs:
```kt
fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()
    html.init()
    return html
}
```




### mockmvc DSL structure
```kt
mockMvc.put(urlTemplate = baseUrl, null, dsl = { ->
// as there is a vararg parameter in the middle, the last lambda block needs to be called by named argument if not called outside the parenthesis.
contentType = MediaType.APPLICATION_JSON
content = objectMapper.writeValueAsString(accountNumber)
})
```

### Inline Functions, Reflections/KClass // MyClass::class || Function references // ::isOdd

> Reflection and Function Reference
```kt
val c = MyClass::class // most basic reflection, also objectName::class

// Function Reference
fun isOdd(x: Int) = x % 2 != 0
val numbers = listOf(1, 2, 3)
println(numbers.filter(::isOdd))
// ::isOdd is a value of function type (Int) -> Boolean
```

```kt
//properties as first-class objects
val x = 1 // defined in the object/class scope. Not inside functions scope

fun main() {
    println(::x.get())
    println(::x.name)
}
```

### java/kotlin Static Method || companion object:
```java
class Foo {
  public static int a() { return 1; }
}

Foo.a();
```
```kt
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

```kt
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

```kt
class Foo {
  companion object Blah {
    fun a() : Int = 1;
  }
}
```

</details>

### Primary & secondary contructors:
Note: if class has primary constructor, Secondary Constructor needs to be delegated to the primary constructor. The compiler select which constructor to use depending on the (number) supplied parameters.
```kt
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

```kt
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

### Invariance, Covariance, Contravariance:
- Invariance: A generic class is called invariant on the type parameter when for two different types A and B, Class<A> is neither a subtype nor a supertype of Class<B>

- Covariance (subtyping relation): A generic class is called covariant on the type parameter when the following holds: Class<A> is a subtype of Class<B>


- Contravariance (Supertype relation or reverse Covariance): Contravariance describes a relationship between two sets of types where they subtype in opposite directions (Supertype)
```kt
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
### Generics Variance (in/out) or variance annotation:
 - out: When type parameter is to only returned (produced/out) from members of Source<T>, and never consumed.
```kt
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
```kt

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

### Star Projection (Generics):
- Function<*, String> means Function<in Nothing, String>.
- Function<Int, *> means Function<Int, out Any?>.
- Function<*, *> means Function<in Nothing, out Any?>.
https://typealias.com/guides/star-projections-and-how-they-work/