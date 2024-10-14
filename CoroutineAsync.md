## Asynchronous Programming Kotlin:
Everything that apply to Java also works here.
https://kotlinlang.org/docs/async-programming.html#futures-promises-and-others

https://hackernoon.com/asynchronous-programming-techniques-with-kotlin-fg9l3wjn

https://www.baeldung.com/java-asynchronous-programming .

### Sequential vs Concurrent vs Lazy Coroutines
async and launch are concurrent, where regrular suspending functions are sequential unless applied threading or channel mechanism.
```kotlin
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
```

* Result
```txt
Sequential Coroutine -------------------- 

On Sequential: The answer is 42
On Sequential: Completed in 2051 ms

Concurrent Coroutine -------------------- 

On Concurrent: The answer is 42
On Concurrent: Completed in 1023 ms

Lazy Coroutine -------------------- 

On Concurrent Lazy: The answer is 42
On Concurrent Lazy: Completed in 1017 ms

Process finished with exit code 0
```

### Shared mutable state and concurrency:
https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html.......