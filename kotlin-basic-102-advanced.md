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

### Function Reference Type and Capturing Value:
1st way, it's simply by using `::fun` referencing....
```kotlin
fun makeIncrementor(amount: Int): () -> Unit {
    var runningTotal = 0;
    fun incrementor(): Unit {
        runningTotal += amount;
        println(runningTotal);
    }
    return ::incrementor;
    // return { increment() } // will also work as it's returning lambda function
}

fun main() {
    val incrementByTen = makeIncrementor(10);
    incrementByTen(); // print 10
    incrementByTen(); // print 20
}
```

When the returning function has parameter
```kotlin
fun makeIncrementor(amount: Int): (Int) -> Unit {
    var runningTotal = 0;
    // instead of returning after. the function can be returned itself. But that function needs to be anonymous
    // return fun (sInt: Int): Unit {...}
    return fun (sInt: Int): Unit {
        runningTotal += amount;
        println(runningTotal);
        println(sInt);
    }
    // return ::incrementor; // will work and neat way
	// return {v -> incrementor(v)} // lambda way needs to pass arg
}

fun main() {
    val incrementByTen = makeIncrementor(10);
    incrementByTen(30); // print 10 & 30
    incrementByTen(40); // print 20 & 40
}
```

### `Context` and Extension functions  
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

### Class Implementing Function Type `invoke impl` | not SAM:
Class can implement a functional type as interface through its invoke function.
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

### Kotlin Builders DSLs: 
```kotlin
fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()
    html.init()
    return html
}
```
### `mockmvc` DSL structure:
```kotlin
mockMvc.put(urlTemplate = baseUrl, null, dsl = { ->
// as there is a vararg parameter in the middle, the last lambda block needs to be called by named argument if not called outside the parenthesis.
contentType = MediaType.APPLICATION_JSON
content = objectMapper.writeValueAsString(accountNumber)
})
```

### Reflections/KClass `MyClass::class` || Function references `::fn`: 

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

### Note on `invoke`:
in kotlin invoke mimics the `()` after function/object/class 's `instance`. using it, we can call a functions's instance like a function, ex `val f = fun (){....}`, here f is an instance of the assigned function. Its same goes with other type (object/class) instances....

inside class/object definition, we declare `operator fun invoke(...){....}` to run the function using `instance(...)` signature.

`invoke` is often very useful when calling a Nullable instance. `let` is another alternative for nullable?.invoke(....) call

```kotlin
fun greetingFun(name: String) = "Hello, $name from greetingFun"
private var sth: ((v: String) -> Unit)? = null // Defining a nullable functional instance

fun main() {
    val greeting = { name: String -> "Hello, $name!" }
    println(greeting.invoke("World"))
    println(greeting("World!"))

    // println((::greeting)("World!")) // error, References to variables and parameters are unsupported
    // println((::greeting).invoke("World!")) // error, same

    println((::greetingFun)("World!")) // ok, as `greetingFun` is not a variable/var/val, its a function    
    // println(greetingFun.invoke("World!")) // error, will not work, 'greetingFun(...)' expected    
    println(::greetingFun.invoke("World!!")) // ok

    sth = { i -> println("Calling Nullable: $i") } // assigning a function to the nullable instance
    sth?.invoke("using invoke")
    sth?.let { it("Using let") }
    // if (sth != null) sth("Using Null Check") // will not work, ide will suggest for sth?.invoke(....)

    sth = fun (s: String) = println("\nNeat way to call a $s")
    sth?.invoke("Nullable")

    sth?.also { it.invoke("Functional Nullable Property") }
    sth?.let{ it(("Something Else")) }
    
    // with if check, making a local copy will work without invoke, but not practical
    if (sth != null) {
        val localCopySth = sth
        if (localCopySth != null) {
            localCopySth("Using Null Check")
        }
    }
}
```
### Function Reference `::fn` and Invoke:
- Function reference `::fn` should be wrapped in and `()` before call, like `(::fn)()`. 
- Invoke can also be used instead like `::fn.invoke()`

```kotlin
fun main() {
    val greeting = { name: String -> "Hello, $name!" }
    println(greeting.invoke("World")) // ok
    println(greeting("World!")) // ok
    // ::greeting will not work as it's defined as variable. only function can be referred using ::fun
    
    fun greeting2(name: String) = "Hello2, $name!"
    // println(::greeting2("World2!")) // error: This syntax is reserved for future use
    println((::greeting2)("World2!")) // ok
    println(::greeting2.invoke("World2!")) // ok
    println(greeting2("World2!")) // ok
}
```
### map vs flatMap
```kotlin
// map()
fun <T, R> Iterable<T>.map(transform: (T) -> R): List<R>

// flatMap()
fun <T, R> Iterable<T>.flatMap(transform: (T) -> Iterable<R>): List<R>
```
https://www.baeldung.com/kotlin/map-vs-flatmap....
### Iterable.flatten():