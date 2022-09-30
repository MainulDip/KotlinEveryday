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

### Job States:
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

### async/await vs launch Coroutine | Deferred<T>/Job :
async starts a new coroutine and returns a Deferred object when completed. Deferred is like Future or Promise. await() can be called on the Deferred instance from async call. If there is a list of deferred objects, it's possible to call awaitAll() to await the results of all of them.

The main difference between async and launch is that launch is used to start a computation that isn't expected to return a specific result. launch returns Job, representing the coroutine. It is possible to wait until it completes by calling Job.join().

Deferred is a generic type that extends Job. An async call can return a Deferred<Int> or Deferred<CustomType> depending on what the lambda returns.

```kotlin
fun main() = runBlocking {
    val deferred: Deferred<Int> = async {
        loadData()
    }
    println("waiting...")
    println(deferred.await())
    println("After Completion, Exiting MainFun")
}

suspend fun loadData(): Int {
    println("loading...")
    delay(1000L)
    println("loaded!")
    return 42
}
```
- awaitAll()
```kotlin

fun main() = runBlocking {
    val deferreds: List<Deferred<Int>> = (1..3).map {
        async {
            delay(1000L * it)
            println("Loading $it")
            it
        }
    }
    val sum = deferreds.awaitAll().sum()
    println("$sum")
}
```

### Concurrency:
Suspending functions doesn't provide concurrency by default. If context parameter of the CoroutineScope.launch() or CoroutineScope.async() is not defined, the suspend function will run on main UI thread. To use different thread use the Dispatchers.Default context
```kotlin
async(context = Dispatchers.Default) { }
launch(context = Dispatchers.Default) { }
```

### Dispatchers:
Dispatchers class groups various implementations of CoroutineDispatcher. CoroutineDispatcher's implementation tree is something like AbstractCoroutineContextElement <-  CoroutineContext.Element <- CoroutineContext

- CoroutineDispatcher determines what thread or threads the corresponding coroutine should be run on. If not specified one as an argument, async/launch will use the dispatcher from the outer scope. Also if outer scope's CoroutineDispatcher/context was not defined, it will use main thread.

- Dispatchers.Default represents a shared pool of threads on JVM. This pool provides a means for parallel execution. It consists of as many threads as CPU cores available, but it still has two threads if there's only one core.

### Structured concurrency:
This is The mechanism providing the structure of the coroutines by defining the scope (CoroutineScope) and parent-child relationships between different coroutines. New coroutines usually need to be started inside a scope. The implicit scope is the CoroutineScope for launch, async, or runBlocking. launch and async are declared as extensions to CoroutineScope, so an implicit or explicit receiver must always be passed when you call them.

The nested coroutine (started by launch or await) can be considered as a child of the outer coroutine (started by runBlocking). This "parent-child" relationship works through scopes; the child coroutine is started from the scope corresponding to the parent coroutine.

It's possible to create a new scope without starting a new coroutine using coroutineScope function, which automatically becomes a child of the outer scope that this suspend function is called from.

```kotlin
fun main() {
    runBlocking {
        doSomething() // It will block code execution but will release underlying thread while not in use
        doSomethingSecond() // It will be executed whenever doSomething() over it is finished.
        otherWay()
    }
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

// Output

// hi: Whenever delay 1000ms suspend over it is finished
// hi: Whenever delay 2000ms suspend over it is finished
// hi: After Coroutine Scope and delay 1000ms suspend over it is Finished
// Test
```


### Global Scope:
To create a new coroutine from the global scope, GlobalScope.async or GlobalScope.launch can be used. This will create a top-level "independent" coroutine. The coroutines started from the global scope are all independent; their lifetime is limited only by the lifetime of the whole application. It's possible to store a reference to the coroutine started from the global scope and wait for its completion or cancel it explicitly, but it won't happen automatically as it would with a structured one.

### Cancellation of Coroutine:
https://kotlinlang.org/docs/cancellation-and-timeouts.html#making-computation-code-cancellable
```kotlin
val startTime = System.currentTimeMillis()
val job = launch(Dispatchers.Default) {
    var nextPrintTime = startTime
    var i = 0
    while (isActive) { // cancellable computation loop
        // print a message twice a second
        if (System.currentTimeMillis() >= nextPrintTime) {
            println("job: I'm sleeping ${i++} ...")
            nextPrintTime += 500L
        }
    }
}
delay(1300L) // delay a bit
println("main: I'm tired of waiting!")
job.cancelAndJoin() // cancels the job and waits for its completion
println("main: Now I can quit.")
```
### Channels (communication between multiple coroutines):
Channels are communication primitives that allow passing data between different coroutines.

A coroutine that sends (produces) information is often called a producer, and a coroutine that receives (consumes) information is called a consumer. Several coroutines can send information to the same channel, and several coroutines can receive data from it.

Channel is represented with three different interfaces: SendChannel, ReceiveChannel, and Channel that extends the first two. You usually create a channel and give it to producers as a SendChannel instance so that only they can send it to it. You give a channel to consumers as a ReceiveChannel instance so that only they can receive from it. Both send and receive methods are declared as suspend.

```kotlin
interface SendChannel<in E> {
    suspend fun send(element: E)
    fun close(): Boolean
}

interface ReceiveChannel<out E> {
    suspend fun receive(): E
}

interface Channel<E> : SendChannel<E>, ReceiveChannel<E>
```

The producer can close a channel to indicate that no more elements are coming.


### Channels Types:
- Unlimited: Channels with no buffered size, the send call will never suspend
- Buffered: When the channel is full, the next `send` call on it suspends until more free space appears.
- Rendezvous: send call will be suspended untill recieve call.
- Conflated: send call will overwrite previous call and receive call will get the latest element always

NB: Rendezvour means -> a meeting at an agreed time and place, typically between two people
```kotlin
val rendezvousChannel = Channel<String>() // By default, a "Rendezvous" channel is created.
val bufferedChannel = Channel<String>(10)
val conflatedChannel = Channel<String>(CONFLATED)
val unlimitedChannel = Channel<String>(UNLIMITED)
```

Example: Rendezvous Channel:
```kotlin
fun main() = runBlocking<Unit> {
    val channel = Channel<String>()

    // Producers
    // Coroutine A
    launch {
        channel.send("A1")
        channel.send("A2")
        log("A1 and A2 Sending done")
    }

    // Producers
    // Coroutine B
    launch {
        channel.send("B1")
        log("B1 Sending done")
    }

    // Consumers
    launch {
        repeat(3) {
            val x = channel.receive()
            log("Received Logging: $x")
        }
    }
}

fun log(message: Any?) {
    println("[${Thread.currentThread().name}] $message")
}

// Results:
// Producers A -> Producers B -> Consumer -> Producer A -> Producer B -> Consumer

// Because of Rendezvous channel (No Buffer), one operation/coroutine can be handled at a time and as no receiver call is found First A1 then B1's "send" calls get suspended.
// On First Receiver Iteration, consumer found producer A1 "send" in channel, so without suspending the receiver/consumer coroutine continues by releasing A1 call from suspended queue
// Because Our Main Thread is busy, A Coroutine's resume gets hold in the queue and the consumer/receiver's iteration continues after running log(x)
// On Second Iteration, B1 Producer's "send" call is found in channel, so without suspending iteration continues after resuming B1 producer coroutine
// Again, as our main thread is busy, B Producers Coroutine get hold in the queue after A coroutine and run lox(x)
// On Third Iteration, consumer find no "send" call in queue, hence suspend the consumer coroutine and start resuming coroutine from the queue, starting from A Coroutine
// Upon A coroutine resumed, A2 producers "send" call does not suspend as it immediately find the Consumer/Receiver Call from the channel, so it completes consumer's coroutine and put in queue at last
// As A2 call does not suspend, it continues by logging log("A1 and A2 Sending done") and complete the coroutine and release for garbage collector
// Then from queue, B Coroutine completes by logging log("B1 Sending done") and then removed by garbage collector
// Last, it resume the consumer coroutine from the queue, log("Received Logging: $x") and complete and then removed by garbage collectors.

// [main] Received Logging: A1
// [main] Received Logging: B1
// [main] A1 and A2 Sending done
// [main] B1 Sending done
// [main] Received Logging: A2
```


### Coroutine Cancelation of execution:
To get complete control, coroutine provide cancelation of it. All the suspending functions in kotlinx.coroutines are cancellable.

Ex: the program will only iterate few times instade of 1000 times, because it will be cancled after 1300ms.
```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
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
```
### Computation and cancellation:
All the suspending functions in kotlinx.coroutines are cancellable. They check for cancellation of coroutine and throw CancellationException when cancelled. However, if a coroutine is working in a computation and does not check for cancellation, then it cannot be cancelled.

Below, when a thread is busy in computation, it cannot be canelled. But if there is any suspending activity while on computation, then it can be canclled.
```kotlin
fun main() = runBlocking {
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
```

* isActive : isActive is an extension property available inside the coroutine via the CoroutineScope object. it can be used to make any computation code cancellable.
```kotlin
while (isActive) {} // cancellable computation loop 
job.cancelAndJoin() // cancels the job and waits for its completion
```

### coroutine try/catch | try/finally | try/finally withContext(NonCancellable):

* try/catch: will throw error if coroutine is cancelled
```kotlin
```

* try/finally: will not throw error if cancelled while on computation

* try/finallay withContext(NonCancellable): if cancellation is called, try block will cancle but withContext(NonCancellable) block inside finally cannot be cancelled.
```kotlin
val job = launch {
    try {
        repeat(1000) { i ->
            println("job: I'm sleeping $i ...")
            delay(500L)
        }
    } finally {
        withContext(NonCancellable) {
            println("job: I'm running finally")
            delay(1000L)
            println("job: And I've just delayed for 1 sec because I'm non-cancellable")
        }
    }
}
delay(1300L) // delay a bit
println("main: I'm tired of waiting!")
job.cancelAndJoin() // cancels the job and waits for its completion
println("main: Now I can quit.")
```

### Coroutine Timeout and Asynchronous timeout:

### suspending coroutine vs non-suspending coroutine (launch, async):
when a suspending function is called, it blocks code execution until finished. In the below code, the 1st defined withTimeout (which is a suspend function) will finished first becaiuse of the suspending nature. After the 1st finished it will call launch (at 2nd) and withTimeout (at 3rd place), after calling the suspending withTimeout at 3rd place, it will again block the code execution further. Between the 2nd and 3rd, it will print which will complete first. Only after completing 3rd suspending the code below will start to executing. launch is a non-suspending coroutine builder, hence it doesn't block and allow to execute withTimeout. 
```kotlin
suspend fun testDualWithTimeoutInsideParentCoroutineScope() = coroutineScope {
    // 1st
    withTimeout(3300L) {
            delay(3200L)
            println("3200L Delay suspending coroutine at 1st")
    }

    // 2nd
    launch {
        delay(1000L)
        println("from launch 1000L at 2nd")
    }

    // 3rd
    withTimeout(2000L){
        delay(1900L)
        println("1900L Delay suspending coroutine at 3rd")
    }

    // 4th
    launch {
        delay(400L)
        println("from launch 400 at 4th")
    }

    // 5th
    withTimeout(700L) {
            delay(200L)
            println("200L Delay suspending coroutine at 5th")
    }
}

fun main() {
    runBlocking {
        testDualWithTimeoutInsideParentCoroutineScope()
    }
}

// prints
// 3200L Delay suspending coroutine at 1st
// from launch 1000L at 2nd
// 1900L Delay suspending coroutine at 3rd
// 200L Delay suspending coroutine at 5th
// from launch 400 at 4th
```