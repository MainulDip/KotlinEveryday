package coroutines

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.*


fun main() = runBlocking<Unit> (Dispatchers.Default) {
    val channel = Channel<String>(1)

    // Producers
    launch {
        channel.send("A1")
        channel.send("A2")
        log("A1 and A2 Sending done")
    }

    // Producers
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
// Producers A1 -> Producers B1 -> Consumer

// Because of no Buffer size, one operation can be handled at a time and as no receiver call is found First A1 then B1 call gets usspended.
// On First Receiver Iteration, consumer found producer A1
// On Second Iteration, consumer found producer B2, so Producers B2 "send" finish the suspending and log("B1 Sending Done")
// On Third Iteration, consumer found nothing else, hence gets suspended and blocks log(x) call
//

// [DefaultDispatcher-worker-2] Received Logging: A1 // Fist Consumer Iteration
// [DefaultDispatcher-worker-2] Received Logging: B1 // Second Consumer Iteration
// [DefaultDispatcher-worker-2] B1 Sending done
// [DefaultDispatcher-worker-1] Received Logging: A2 // Third Consumer Iteration
// [DefaultDispatcher-worker-3] A1 and A2 Sending done