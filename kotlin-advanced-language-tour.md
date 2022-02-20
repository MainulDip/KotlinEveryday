## Kotlin Advanced Language Tour Overview
It's a personalized guide and quick tour of the day to day used kotlin language advanced/non-basic syntaxes and features. Like Kotlin DSL (Builders), Context and Extention functions, HigherOrder etc.

### Context and Extention function
```kt
fun call(greet: String.(String) -> Unit) {
// greet("Hello", " Dolly") // greet's 1st parameter is the context and  last param is it's native parameter.
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
<summary>This: Explecit/Implecit</summary>

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
// as there is a vararg parameter in the middle, the last lambda block needs to be called by named argument if not called outside the parenthesis  
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