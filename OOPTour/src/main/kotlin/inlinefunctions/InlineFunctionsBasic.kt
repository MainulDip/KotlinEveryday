package inlinefunctions

fun higherfuncN( str : String, funCall : (String) -> Unit) {
    funCall(str)
}

// making kotlin inline function
inline fun higherfuncI( str : String, funCall : (String) -> Unit) {
    funCall(str)
}

fun main(args: Array<String>) {
    higherfuncN("Testing effect of non-inline and inline functions compiled JVM code"){
        println(it)
    }

    higherfuncI("Testing effect of non-inline and inline functions compiled JVM code"){
        println(it)
    }
}
