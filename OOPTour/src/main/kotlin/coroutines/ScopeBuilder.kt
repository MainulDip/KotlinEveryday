package coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        doSomething()
    }
}

suspend fun doSomething() {
    coroutineScope {
        launch {
            delay(1000L)
            println("hi: Whenever delay 1000ms suspend over it is finished")
        }
        delay(2000L)
        println("hi: Whenever the the delay 2000ms suspend over it is finished")
    } // this also block code executions until finished, but release underlying thread for other uses. Where "runBlocking" blocks the current thread.
    delay(1000)
    println("hi: After Coroutine Scope and delay 1000ms suspend over it is Finished")
}
