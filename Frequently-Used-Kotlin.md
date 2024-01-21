### `is` and `!is` type checking `infix fn`:
Used like (T is V) and returns Boolean. `!is` is the `not` version.

### type casting, `as`, `as?`, `as!`, `T as V?`:
Other than `as?` all of those will throw runtime error if casting failed.

<details>

<summary>Example of `T as V?` vs `T as? V`</summary>

```kotlin
fun main() {
    asChecking(12)
    asChecking("12")
}

fun asChecking(y: Any){
    // val str: String? = y as String? // it's nullable (either null or string), but if casting fails (Int to String), it will throw a runtime exception
    val str2: String? = y as? String // same, but if casting fails, null will be assigned, no runtime error will happen, hence `as? is safe cast`
    println("$str $str2")
}
```

</details>

### invoke with Class/Obj/Functions instance:
`invoke` is a operator function of a class/obj/function. In class/obj we can override it and implement custom code in it. So when it will be called on its instance, the overridden block will be run.

With functions instance, it provide a syntactic sugar for calling a `Nullable` function like `fn?.invoke(param1,param2)`. Otherwise let{} should be used like `fn?.let{it(param1, param2)}`

<details>

<summary>invoke example with nullable function</summary>

```kotlin
fun greetingFun(name: String) = "Hello, $name from greetingFun"
private var sth: ((v: String) -> Unit)? = null

fun main() {
    val greeting = { name: String -> "Hello, $name!" }
    println(greeting.invoke("World"))
    println(greeting("World!"))

    // println((::greeting)("World!")) // error, References to variables and parameters are unsupported
    // println((::greeting).invoke("World!")) // error, same

    println((::greetingFun)("World!")) // ok, as `greetingFun` is not a variable/var/val, its a function
    // println(greetingFun.invoke("World!")) // error, will not work, 'greetingFun(...)' expected
    println(::greetingFun.invoke("World!!")) // ok

    sth = { i -> println("Calling Nullable: $i") }
    sth?.invoke("using invoke")
    sth?.let { it("Using let") }
    // if (sth != null) sth("Using Null Check") // will not work, ide will suggest for sth?.invoke(....)

    sth = fun (s: String) = println("\nNeat way to call a $s")
    sth?.invoke("Nullable")

    sth?.also { it.invoke("Functional Nullable Property") }
    sth?.let{ it(("Something Else")) }

    // with if check, making a local copy will work without invoke
    if (sth != null) {
        val localCopySth = sth
        if (localCopySth != null) {
            localCopySth("Using Null Check")
        }
    }
}
```
</details>

### `when` with `as`, `in`, arbitrary expression and use like `if-else` block:
Any arbitrary expressions (not only constants) can be used as branch conditions on when block.

```kotlin
// when statement as arbitrary express as branch condition
when (x) {
    s.toInt() -> print("s encodes x") // if toInt() succeed, it will run
    else -> print("s does not encode x")
}

// combining multiple combination in when statement
when (x) {
    0, 1 -> print("x == 0 or x == 1")
    else -> print("otherwise")
}

// `in` for checking if in a range in when statement
when (x) {
    in 1..10 -> print("x is in the range")
    in validNumbers -> print("x is valid")
    !in 10..20 -> print("x is outside the range")
    else -> print("none of the above")
}

// checking that a value is or !is of a particular type. Note: here when is an expression as it assigned the returned value to something
fun hasPrefix(x: Any) = when(x) {
    is String -> x.startsWith("prefix")
    else -> false
}
```



### Sealed Class with inner and outer class inheritance:
Sealed class provides better IDE suggestion with `when(condition)`. As it is bound to its defined package. Other Data and Plain classes can also inherit a sealed class within its scoped package and the `when` block will mark for required update implementation.

<details>

<summary>Demo of Sealed class inheritance and when block</summary>

```kotlin
sealed class LatestNewsUiState {
    data class Success(val news: List<String>): LatestNewsUiState()
    data class Error(val exception: String): LatestNewsUiState()
}

fun handleResponse(call: LatestNewsUiState) {
    when (call) {
        is LatestNewsUiState.Error -> println(call.exception)
        is LatestNewsUiState.Success -> println(call.news.joinToString(", "))
        is Processing -> println(call.status)
    }
}

fun main() {
    val networkCallProcessing = Processing()
    handleResponse(networkCallProcessing)

    val netWorkCallSuccess = LatestNewsUiState.Success(listOf("News One", "News Two", "News Three", "News Four"))
    handleResponse(netWorkCallSuccess)

    val newWorkCallError = LatestNewsUiState.Error("Bad Request")
    handleResponse(newWorkCallError)

    println("Type Checking with `is`=> 7 is an Int: ${7 is Int}")
}

class Processing: LatestNewsUiState() {
    val status = "Network call is being processed now"
}
```

</details>