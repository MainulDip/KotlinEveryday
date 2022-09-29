package coroutines

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        testWithTimeout()
    }
}

// test with-timeout coroutine and exception handling

suspend fun testWithTimeout (): CoroutineScope {
    val a = coroutineScope {
        withTimeout(1300L) {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
    }

    return CoroutineScope(Dispatchers.Default)

}