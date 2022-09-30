package coroutines

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        println(testWithTimeout())
        println(testDualWithTimeout())
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