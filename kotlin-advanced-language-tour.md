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