package decompiletesting

inline fun f1(crossinline innc: ()->Unit ,noinline innn: ()->Unit){
    f2 { innc() }
    f2 { innn() }
}

fun f2(inn: ()->Unit){
    inn()
}

fun main() {
    f1 ({
        println("crossinline callback")
    }, {
        println("noinline callback")
    })
}