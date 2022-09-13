package coroutines

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.*


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