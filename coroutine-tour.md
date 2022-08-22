## Coroutine Dedicated Mini Doc:
This markdown is to provide jump start docs for Coroutine in kotline projects (Spring/Android). It provide the following topics:
- [Setup and Basic](#setup-coroutine-basic)
- [Hands-on: Intro to coroutines and channels](#)
- [Cancellation and timeouts](#)
- [Composing suspending functions](#)
- [Coroutine Context With Dispatchers](#coroutine-context-dispatchers)
- [Asynchronous Flow](#)
- [Channels](#)
- [Coroutine exceptions handling](#)
- [Shared mutable state and concurrency](#)
- [Select expression (experimental)](#)

### Setup and Basic Coroutine:
Kotlin natively supports Asynchronous or non-blocking programming by providing Coroutine support in the standard library. Also Coroutine provide concurrency and actors.

- setup: Add dependencies as applicable by build tools variations.
https://github.com/Kotlin/kotlinx.coroutines/blob/master/README.md#using-in-your-projects

### Basic Coroutine and Suspend Function:

```kotlin
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
```

coroutines are lightweight threads. By lightweight, it means that creating coroutines doesn’t allocate new threads. Instead, they use predefined thread pools and smart scheduling for the purpose of which task to execute next and which tasks later.

- runBlocking: ( coroutine builder that bridges the non-coroutine world of a regular fun main() ). It Runs a new coroutine and blocks the current thread until its completion. This function should not be used from (inside) a coroutine. It is designed to bridge regular blocking code to libraries that are written in suspending style, to be used in main functions and in tests.
```kotlin
// Signature
expect fun <T> runBlocking(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T): T
```

- launch: a coroutine builder. It launches a new coroutine concurrently with the rest of the code, which continues to work independently.
```kotlin
// Signature
fun CoroutineScope.launch(    context: CoroutineContext = EmptyCoroutineContext,     start: CoroutineStart = CoroutineStart.DEFAULT,     block: suspend CoroutineScope.() -> Unit): Job
```

- delay: It's a suspend function. It delays coroutine for a given time without blocking a thread and resumes it after a specified time.
```kotlin
// Signature
suspend fun delay(timeMillis: Long)
```

### Structured concurrency:
Coroutines follow a principle of structured concurrency which means that new coroutines can be only launched in a specific CoroutineScope which delimits the lifetime of the coroutine. 

For example, "runBlocking" establishes the corresponding scope and that is why the previous example waits until every job is finished then exits.

Structured concurrency ensures that they are not lost and do not leak. An outer scope cannot complete until all its children coroutines complete. Structured concurrency also ensures that any errors in the code are properly reported and are never lost.
### Coroutine Context With Dispatchers:

### Suspend Function and Coposing:
Suspend function is a function that could be started, paused, and resume. They are only allowed to be called from a coroutine or another suspend function.