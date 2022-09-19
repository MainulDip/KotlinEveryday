package coroutines

import kotlinx.coroutines.*

fun main() = runBlocking {
//    nonComputational()
    cancellableExceptions()
//    computational()
}

suspend fun nonComputational() = coroutineScope {
    val job = launch {
        repeat(1000) { i ->
            println("job: I'm sleeping $i ...")
            delay(500L) // this makes the loop suspend for 500ms, in this time the thread gets some time to cancle the computation and call the canclable call
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job
    job.join() // waits for job's completion
    println("main: Now I can quit.")
}

suspend fun computational() = coroutineScope {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) { // computation loop, just wastes CPU
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                // this line force not to exit while loop by blockiing i++ in  consitionals
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
                // delay(500L) // this makes the loop suspend for 500ms, in this time the thread gets some time to cancle the computation and call the canclable call
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job and waits for its completion
    job.join()
    println("main: Now I can quit.")
}

suspend fun cancellableExceptions() = coroutineScope {
    val job = launch(Dispatchers.Default) {
        repeat(5) { i ->
            try {
                // print a message twice a second
                println("job: I'm sleeping $i ...")
                delay(500)
            } catch (e: Exception) {
                // log the exception
                println(e)
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}