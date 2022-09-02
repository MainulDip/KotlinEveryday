package inlinefunctions

fun baz (z: () -> String) {
    z()
}

inline fun foo(f: () -> String) {
    f()
}

fun main() {
    foo(fun(): String {
        println("HelloFoo")
        return "compiled" // local return. return to the anonymous function
    })

    baz {
        println("HelloBaz") // why inline function's lambda return "String" is ignored
        // return // IDE => 'return' is not allowed here => change to return@baz
        ""
    }

    foo {
        println("HelloFooSecond") // why inline function's lambda return "String" is ignored
        return // non-local returns. skipped and return as "main" function's "Unit" return
    }

    println("Not print, skipped")

}