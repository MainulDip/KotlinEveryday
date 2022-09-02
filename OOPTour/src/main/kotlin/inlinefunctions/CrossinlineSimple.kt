package inlinefunctions

// if in an inline function the parameter lambdas parameter is passed to a non-inline//regularsr
// function's lambda context, we must declare the "crossinline"

inline fun foos(crossinline g: () -> Unit) {
    bar { g() }
}

fun bar(f: () -> Unit) {
    f()
}

fun main(){
    foos {
        println("Simple Cross-inline")
//         return // not allowed
    }
}

// analyze the decompiled code