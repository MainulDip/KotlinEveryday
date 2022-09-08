package samples

import kotlinx.coroutines.*

//fun main() = runBlocking {
//    val deferred: Deferred<Int> = async(Dispatchers.Default) {
//        loadData()
//    }
//    log("waiting...")
//    log(deferred.await())
//}

//suspend fun main() {
//    callSuspend()
//}

fun main() {
    callFromMain()
}

suspend fun loadData(): Int {
    log("loading...")
    delay(1000L)
    log("loaded!")
    return 42
}

suspend fun callSuspend() = coroutineScope {
        val deferred: Deferred<Int> = async(Dispatchers.Default) {
            loadData()
        }
        log("waiting...")
        log(deferred.await())
}

fun callFromMain() {
    runBlocking {
        launch {
            callSuspend()
        }
    }
}