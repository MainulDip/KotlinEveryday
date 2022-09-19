package coroutines

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        doSomething() // It will block code execution but will release underlying thread while not in use
        doSomethingSecond() // It will be executed whenever doSomething() over it is finished.
        otherWay()
    }
}
// Output

// hi: Whenever delay 1000ms suspend over it is finished
// hi: Whenever delay 2000ms suspend over it is finished
// hi: After Coroutine Scope and delay 1000ms suspend over it is Finished
// Test

suspend fun doSomething() {
    coroutineScope {
        // code inside is non-blocking, hence work concurrently
        launch {
            delay(2000L)
            println("hi: Whenever delay 2000ms suspend over it is finished")
        }
        delay(1000L) // delay will suspend the thread until it is finished
        println("hi: Whenever delay 1000ms suspend over it is finished")
    } // this also block code executions until finished, but release underlying thread for other uses. Where "runBlocking" blocks the current thread.
    delay(1000)
    println("hi: After Coroutine Scope and delay 1000ms suspend over it is Finished")
}

suspend fun doSomethingSecond(){
    coroutineScope {
        launch {
            println("Test")
        }
    }
}

suspend fun otherWay(){
    var cs = CoroutineScope(Dispatchers.Default)
    var job = cs.launch {
        delay(1000L)
        println("Good")
    }
    job.join()

    println("It is done")

}