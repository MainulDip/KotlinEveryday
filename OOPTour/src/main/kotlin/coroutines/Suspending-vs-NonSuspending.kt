package coroutines

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        testLaunch ()
//        prints
//        after 2000L launch
//        after 1000L launch
//        2nd launch 1000L
//        1st launch 2000L
//        after coroutine scope

        testDualWithTimeoutInsideParentCoroutineScope()
//        prints
//        3200L Delay suspending coroutine at 1st
//        from launch 1000L at 2nd
//        1900L Delay suspending coroutine at 3rd
//        200L Delay suspending coroutine at 5th
//        from launch 400 at 4th
    }
}

suspend fun testLaunch () {
    // each coroutineScope call is itself a suspending function, hence it will be handled one by one, but launch is non-suspending, hence more launch can work concurrently in same coroutineScope
    coroutineScope {
        launch {
            delay(2000L)
            println("1st launch 2000L")
        }
        println("after 2000L launch")
        launch {
            delay(1000L)
            println("2nd launch 1000L")
        }
        println("after 1000L launch")
    }
    println("after coroutine scope") // this will print only after the parent coroutine scope finished
}

suspend fun testDualWithTimeoutInsideParentCoroutineScope() = coroutineScope {
    // 1st
    withTimeout(3300L) {
        delay(3200L)
        println("3200L Delay suspending coroutine at 1st")
    }

    // 2nd
    launch {
        delay(1000L)
        println("from launch 1000L at 2nd")
    }

    // 3rd
    withTimeout(2000L){
        delay(1900L)
        println("1900L Delay suspending coroutine at 3rd")
    }

    // 4th
    launch {
        delay(400L)
        println("from launch 400 at 4th")
    }

    // 5th
    withTimeout(700L) {
        delay(200L)
        println("200L Delay suspending coroutine at 5th")
    }
}