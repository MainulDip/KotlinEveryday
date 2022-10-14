package asynchronous_coroutine_advanced

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        println("The answer is ${concurrentSum()}")
    }
    println("Completed in $time ms")
}
// structured parent coroutine
suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { childSuspendOne() }
    val two = async { childSuspendTwo() }
    one.await() + two.await()
}

suspend fun childSuspendOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun childSuspendTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}