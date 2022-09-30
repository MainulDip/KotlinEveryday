package coroutines

import kotlinx.coroutines.*

fun main() {
    runBlocking {
//        println(testWithTimeout())
//        println(testDualWithTimeout())
        testDualWithTimeoutInsideParentCoroutineScope()

//        testLaunch()
    }
}

// test with-timeout coroutine and exception handling

suspend fun testDualWithTimeout() {
    withTimeout(1300L) {
        repeat(1) { i ->
            delay(500L)
            println("I'm sleeping $i ... A")
        }
    }

    withTimeout(1300L) {
        repeat(1) { i ->
            delay(200L)
            println("I'm sleeping $i ... B")
        }
    }
}

//I'm sleeping 0 ... A
//I'm sleeping 0 ... B

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

suspend fun testWithTimeout () : CoroutineScope {
        val a = withTimeout(1300L) {
            repeat(1) { i ->
                println("I'm sleeping $i ... A")
                delay(500L)
            }
//            return@withTimeout this
            this
        }
    return a
}

suspend fun testLaunch (){
    // each coroutine scope will be handled one by one, but blocs inside the parent coroutine scope will be handled concurrently
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