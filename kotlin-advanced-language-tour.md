## Kotlin Advanced Language Tour Overview
This markdow file provides mini docs for following topics:
- [Context and Extention functions](#eontext-extension)
- [Kotlin Builders DSLs](#dsl-builders)
- [mockmvc DSL structure](#mockmvc-dsl)
- [Inline Functions, Reflections/KClass // MyClass::class || Function references // ::isOdd](#inline-reflection-function-reference)


### Functions Receiver Type in Parameter:
Non-literal (not-exact/type) values of function types with and without a receiver are interchangeable, so the receiver can stand in for the first parameter, and vice versa. For instance, a value of type (A, B) -> C can be passed or assigned where a value of type A.(B) -> C is expected, and the other way around. And can be called as both extension and regular way.
```kotlin
fun main() {
    /**
     * Here repeatFun behaves like an extension function of String and a regular function also
     * But for towParameters, as the type is explicit there, cannot be an extension function even though it assaign `repeatFun` 
     * Same way the `repeatFun2` behaves both extension and regular way (because of same signature/type)
     */
    val repeatFun: String.(Int) -> String = { times -> this.repeat(times) } /* supports calling by `repeatFun("String", 4)` and `"String".repeatFun(4)` both */
    val twoParameters: (String, Int) -> String = repeatFun /* supports calling only by `twoParameters("Sring",4)`, because of explicit type of not beign an extension of String */
    val repeatFun2: String.(Int) -> String = twoParameters /* supports both */
    
    println(repeatFun("G", 4)) // will work, returns GGGG
    println("G".repeatFun(4)) // will work, returns GGGG
    // println("G".twoParameters(4)) // will not work
    println(twoParameters("G",4)) // will work, returns GGGG
    println("G".repeatFun2(4)) // will work, returns GGGG
    println(repeatFun2("G",4)) // will work, returns GGGG
    
    fun runTransformation(f: (String, Int) -> String): String {
        return f("hello", 3)
    }
    val result = runTransformation(repeatFun)
    val result2 = runTransformation(twoParameters)
    
    println("result = $result") // result = hellohellohello
    println("result2 = $result2") // result2 = hellohellohello
}
```

### <a name="context-extension"></a> Context and Extension functions  
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

### Class Implementing Function Type:
Use instances of a custom class that implements a function type as an interface
```kotlin
/**
 * A class implementing a Functional Type (Not SAM) have to do it as invoke override
 */
class IntTransformer: (Int) -> Int {
    override operator fun invoke(x: Int): Int {
    	println(x)
        return x + 1
    }
}

fun main() {
    val intFunction = IntTransformer()
    // inferred type from `val intFunction: (Int) -> Int = IntTransformer()`
    // println(intFunction.invoke(3)) // will work but we don't need to call invoke explicitly.
    println(intFunction(3)) // instance() call will trigger the invoke anyway   
}
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

### `This` Explicit/Implicit:

```kotlin
fun main() {
    A().B().run { 12.foo() }   
}

class A { // implicit label @A
    inner class B { // implicit label @B
        fun Int.foo() { // implicit label @foo
            val a = this@A // A's this
            val b = this@B // B's this
            println("${a::class.simpleName} and ${b::class.simpleName}") // prints `A and B`

            val c = this // foo()'s receiver, an Int.
            val c1 = this@foo // foo()'s receiver, an Int
            println("${c::class.simpleName} : $c and ${c1::class.simpleName} : $c1")  // prints `Int : 12 and Int : 12`

            val funLit = lambda@ fun String.() {
                val d = this // funLit's receiver
                println(d)
            }
            
            "G".funLit() // will print `G`

            val funLit2 = { s: String ->
                // foo()'s receiver, since enclosing lambda expression
                // doesn't have any receiver
                val d1 = this
                println("d1 = $d1 form ${d1::class.simpleName}")
            }
            
            funLit2("Something") // prints `d1 = 12 form Int`
        }
    }
}

// A and B
// Int : 12 and Int : 12
// G
// d1 = 12 form Int
```

Note: "this" can be omitted if called in a member function. But if there is a function in the outer scope, "this" is necessary to call member function. Without "this" receiver, top level function will be called.
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

### <a name="dsl-builders"></a> Kotlin Builders DSLs: 
```kotlin
fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()
    html.init()
    return html
}
```




### <a name="mockmvc-dsl"></a>mockmvc DSL structure:
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