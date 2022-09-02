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

- launch: a non-blocking (concurrent) coroutine builder. It launches a new coroutine concurrently with the rest of the code, which continues to work independently.
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

### Scope Builder:
Cusotom Coroutine Scope can be defined using coroutineScope, coroutineScope is an interface. Bloks (not thread) code execution until finished.
```kotlin
interface CoroutineScope
```
Will reside inside of a suspend function. It Defines a scope for new coroutines. Every coroutine builder (like launch, async, etc.) is an extension on CoroutineScope and inherits its coroutineContext to automatically propagate all its elements and cancellation.

CoroutineScope will be reside inside runBlocking() builder, like runBlocking, it will block code execution until finished, but release underlying thread for other uses.

This "releasing thread while not in use" behavour is call "suspend".
NB: code inside the coroutine scope will execute concurrently if not suspended.
```kotlin
// CoroutineScope
fun main() {
    runBlocking {
        doSomething() // It will block code execution but will release underlying thread while not in use
        doSomethingSecond() // It will be executed whenever doSomething() over it is finished.
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
```

### Job:
Conceptually, a job is a cancellable thing with a life-cycle that culminates in its completion. An execution of a job does not produce a result value. Jobs are launched solely for their side-effects.

- Job Instances (Basic): most basic instances of Job interface are created like this:
    * Coroutine job is created with launch coroutine builder. It runs a specified block of code and completes on completion of this block.

    * CompletableJob is created with a Job() factory function. It is completed by calling CompletableJob.complete.

### Job State :
 * A job has the following states:
 *
 * | **State**                        | [isActive] | [isCompleted] | [isCancelled] |
 * | -------------------------------- | ---------- | ------------- | ------------- |
 * | _New_ (optional initial state)   | `false`    | `false`       | `false`       |
 * | _Active_ (default initial state) | `true`     | `false`       | `false`       |
 * | _Completing_ (transient state)   | `true`     | `false`       | `false`       |
 * | _Cancelling_ (transient state)   | `false`    | `false`       | `true`        |
 * | _Cancelled_ (final state)        | `false`    | `true`        | `true`        |
 * | _Completed_ (final state)        | `false`    | `true`        | `false`       |

https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/
### Coroutine Context With Dispatchers:

### Suspend Function and Composing :
Suspend function is a function that could be started, paused, and resume. They are only allowed to be called from a coroutine or another suspend function. Suspend functions release the underlying thread for other usages while on paused.