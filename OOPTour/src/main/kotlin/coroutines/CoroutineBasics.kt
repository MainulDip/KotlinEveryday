package coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    // runBlocking will block execution until delay suspend function is finished

    runBlocking { // this: CoroutineScope
        launch { // launch is a coroutine builder. It launches a new coroutine concurrently with the rest of the code, which continues to work independently
            delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
            println("World!") // prints after delay
        }
        println("Hello") // main coroutine continues while a previous one is delayed
    }

    // Second runBlocking will start executing after finishing First

    runBlocking {
        // "Hello 1" and 3rd launch function will print first, because 3rd launch has no delay (delay is a suspend function)
        // 1st and 2nd launch will be printed second as the both has same milliseconds of delay suspend function

        // 1st
        launch {
            delay(1000L)
            println("World 1")
        }

        // 2nd
        launch {
            delay(1000L)
            println("World 2")
        }

        // 3rd
        launch {
            delay(0L)
            println("World 3")
        }

        println("Hello 1")
    }

//    Hello
//    World!
//    Hello 1
//    World 3
//    World 1
//    World 2
}