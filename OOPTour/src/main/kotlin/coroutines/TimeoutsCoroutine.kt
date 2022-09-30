package coroutines

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        // testDualWithTimeout() // after throwing TimeoutCancellationException on result1 it will exit and result2 will not execute
        // testDualWithTimeoutReverse() // if exception is not handle of the previous function call, this code will not execute. When handled, result2 will execute but result1 will throw error
        testDualWithTimeoutExceptionHandling()
//        println(testWithTimeout())
    }
}

// test with-timeout coroutine and exception handling

suspend fun testDualWithTimeout() {

    val result1 = withTimeout(1300L) {
        repeat(7) { i ->
            delay(500L)
            println("I'm sleeping $i ... A")
        }
    }
    println(result1)

    val result2 = withTimeoutOrNull(1300L) {
        repeat(7) { i ->
            delay(700L)
            println("withTimeoutOrNull I'm sleeping $i ... B")
        }
    }
    println(result2)
}

suspend fun testDualWithTimeoutReverse() {

    val result2 = withTimeoutOrNull(1300L) {
        repeat(7) { i ->
            delay(700L)
            println("withTimeoutOrNull I'm sleeping $i ... B")
        }
    }
    println(result2)

    val result1 = withTimeout(1300L) {
        repeat(7) { i ->
            delay(500L)
            println("I'm sleeping $i ... A")
        }
    }
    println(result1)
}

suspend fun testDualWithTimeoutExceptionHandling() {

    try {
        val result1 = withTimeout(1300L) {
            repeat(7) { i ->
                delay(500L)
                println("I'm sleeping $i ... A")
            }
        }
        println(result1)
    } catch (e: TimeoutCancellationException) {
        println(e)
    }

    try {
        val result2 = withTimeoutOrNull(1300L) {
            repeat(7) { i ->
                delay(700L)
                println("withTimeoutOrNull I'm sleeping $i ... B")
            }
        }
        println(result2)
    } catch (e: TimeoutCancellationException){
        println("this will never execute, as withTimeoutOrNull will never throw an exception")
    }
    // withTimeout exception handling
    try {
        val result1 = withTimeout(1300L) {
            repeat(7) { i ->
                delay(500L)
                println("I'm sleeping $i ... A")
            }
        }
        println(result1)
    } catch (e: TimeoutCancellationException) {
        println(e)
    } finally {
        println("printing after catch block finally")
    }

    try {
        val result1 = withTimeoutOrNull(1300L) {
            repeat(7) { i ->
                delay(500L)
                println("I'm sleeping $i ... A")
            }
        }
        println(result1)
    } finally {
        println("printing from second finally")
    }

}

//I'm sleeping 0 ... A
//I'm sleeping 0 ... B


// just curiosity to return a CoroutineScope, maybe not logical anyway
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