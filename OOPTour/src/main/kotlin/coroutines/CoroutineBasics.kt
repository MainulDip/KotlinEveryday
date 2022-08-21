package coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking { // this: CoroutineScope
        launch { // launch is a coroutine builder. It launches a new coroutine concurrently with the rest of the code, which continues to work independently
            delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
            println("World!") // prints after delay
        }
        println("Hello") // main coroutine continues while a previous one is delayed
    }

    runBlocking {
        launch {
            delay(1000L)
            println("World 1")
        }

        println("Hello 1")
    }

//    Hello
//    World!
//    Hello 1
//    World 1
}