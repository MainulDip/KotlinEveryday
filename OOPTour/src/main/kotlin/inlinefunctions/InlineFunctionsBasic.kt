package inlinefunctions

fun higherfunc( str : String, funCall : (String) -> Unit) {
    funCall(str)
}

fun main(args: Array<String>) {
    higherfunc("Testing effect of non-inline and inline functions compiled JVM code"){
        println(it)
    }
}
