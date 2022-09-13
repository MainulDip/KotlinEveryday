## Kotlin Advanced Language Tour Overview
This markdow file provides mini docs for following topics:
- [Context and Extention functions](#eontext-extension)
- [Kotlin Builders DSLs](#dsl-builders)
- [mockmvc DSL structure](#mockmvc-dsl)
- [Inline Functions, Reflections/KClass // MyClass::class || Function references // ::isOdd](#inline-reflection-function-reference)

### <a name="eontext-extension"></a> Context and Extention functions 
```kotlin
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

```kotlin
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

```kotlin
class A { // implicit label @A
    inner class B { // implicit label @B
        fun Int.foo() { // implicit label @foo
            val a = this@A // A's this
            val b = this@B // B's this

            val c = this // foo()'s receiver, an Int.
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
```kotlin
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

### <a name="dsl-builders"></a> Kotlin Builders DSLs: 
```kotlin
fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()
    html.init()
    return html
}
```




### <a name="mockmvc-dsl"></a> Mockmvc DSL structure:
```kotlin
mockMvc.put(urlTemplate = baseUrl, null, dsl = { ->
// as there is a vararg parameter in the middle, the last lambda block needs to be called by named argument if not called outside the parenthesis.
contentType = MediaType.APPLICATION_JSON
content = objectMapper.writeValueAsString(accountNumber)
})
```

### <a name="inline-reflection-function-reference"></a> Inline Functions, Reflections/KClass // MyClass::class || Function references // ::isOdd 

> Reflection and Function Reference
```kotlin
val c = MyClass::class // most basic reflection, also objectName::class

// Function Reference
fun isOdd(x: Int) = x % 2 != 0
val numbers = listOf(1, 2, 3)
println(numbers.filter(::isOdd))
// ::isOdd is the declaration reference of function type (Int) -> Boolean
```

```kotlin
//properties as first-class objects
val x = 1 // defined in the object/class scope. Not inside functions scope

fun main() {
    println(::x.get())
    println(::x.name)
}
```