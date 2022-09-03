package inlinefunctions

// if in an inline function the parameter lambdas parameter is passed to a non-inline//regular
// function's lambda context, we must declare the "crossinline"

inline fun foos(crossinline g: () -> Unit) {
    bar { g() } // crossinlie or noinline is necessary if inline functions callback parameter is passed inside non-inline function's callback
}

fun bar(f: () -> Unit) {
    f()
}

fun main(){
    foos {
        println("Simple Cross-inline")
//         return // non-local return is not allowed
    }
}

// analyze the decompiled code