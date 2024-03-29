package asynchronous_coroutine_advanced

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking<Unit> {
    /**
     * Sequential by Default: suspend function will complete one after another
     */

    println("Sequential Coroutine -------------------- \n")

    val timeS = measureTimeMillis {
        val one = taskOne() // only run when one is finished
        val two = taskTwo()
        println("On Sequential: The answer is ${one + two}")
    }

    println("On Sequential: Completed in $timeS ms")

    /**
     * Concurrent using async or launch : Starts at the same time
     */

    println("\nConcurrent Coroutine -------------------- \n")

    val timeC = measureTimeMillis {
        val one = async { taskOne() } // both will start at the same time
        val two = async { taskTwo() }
        println("On Concurrent: The answer is ${one.await() + two.await()}")
    }
    println("On Concurrent: Completed in $timeC ms")

    println("\nLazy Coroutine -------------------- \n")

    /**
    * On Lazy mode it only starts the coroutine when its result is required by await, or if it's Job's start function is invoked.
    */

    val timeCL = measureTimeMillis {
        val one = async (start = CoroutineStart.LAZY) { taskOne() } // both will start at the same time
        val two = async (start = CoroutineStart.LAZY) { taskTwo() }

        /**
         * if not started by start, it can be sequential on line one.await() + two.await()
         */

        one.start()
        two.start()

        println("On Concurrent Lazy: The answer is ${one.await() + two.await()}")
    }
    println("On Concurrent Lazy: Completed in $timeCL ms")
}

suspend fun taskOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun taskTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

