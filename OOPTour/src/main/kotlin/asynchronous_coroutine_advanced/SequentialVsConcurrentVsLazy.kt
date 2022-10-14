package asynchronous_coroutine_advanced

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking<Unit> {
    println("Sequential Coroutine -------------------- \n")
    val timeS = measureTimeMillis {
        val one = sequentialOne() // only run when one is finished
        val two = sequentialTwo()
        println("On Sequential: The answer is ${one + two}")
    }
    println("On Sequential: Completed in $timeS ms")

    println("\nConcurrent Coroutine -------------------- \n")

    val timeC = measureTimeMillis {
        val one = async { concurrentOne() } // both will start at the same time
        val two = async { concurrentTwo() }
        println("On Concurrent: The answer is ${one.await() + two.await()}")
    }
    println("On Concurrent: Completed in $timeC ms")

    println("\nLazy Coroutine -------------------- \n")
    /*
    * On Lazy mode it only starts the coroutine when its result is required by await, or if its Job's start function is invoked.
    * */

    val timeCL = measureTimeMillis {
        val one = async (start = CoroutineStart.LAZY) { concurrentOne() } // both will start at the same time
        val two = async (start = CoroutineStart.LAZY) { concurrentTwo() }
        println("On Concurrent Lazy: The answer is ${one.await() + two.await()}")
    }
    println("On Concurrent Lazy: Completed in $timeCL ms")
}

/**
 * Sequential by Default: suspend function will complete one after another
 */

suspend fun sequentialOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun sequentialTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

/**
 * Concurrent using async or launch : Starts at the same time
 */

suspend fun concurrentOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun concurrentTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}