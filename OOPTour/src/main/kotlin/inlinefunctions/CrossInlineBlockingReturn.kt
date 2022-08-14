package inlinefunctions

fun main(args: Array<String>){
    println("Main function starts")
    inlinedfunc({
        println("Lambda expression 1")
//        return // It gives compiler error because of the "crossinline" declration
        },
        { println("Lambda expression 2")
            "Hello World"
        }
    )

    println("Main function ends")
}

inline fun inlinedfunc( crossinline lmbd1: () -> Unit, lmbd2: () -> String  ) {
    lmbd1()
    lmbd2()
}