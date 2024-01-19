package org.example

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


// https://medium.com/@dugguRK/mastery-on-invoke-kotlin-8f1ebb4828d0