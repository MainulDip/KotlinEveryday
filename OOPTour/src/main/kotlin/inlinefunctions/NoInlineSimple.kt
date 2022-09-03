package inlinefunctions

// if in an inline function the parameter lambdas parameter is passed to a non-inline//regularsr
// function's lambda context, we must declare the "crossinline"

inline fun foonoinline(noinline g: () -> Unit) {
    bars { g() } // crossinlie or noinline is necessary if inline functions callback parameter is passed inside non-inline function's callback
}

fun bars(f: () -> Unit) {
    f()
}

fun main(){
    foonoinline {
        println("No-Inline Main")
//         return // non-local return is not allowed
    }
}

// analyze the decompiled code