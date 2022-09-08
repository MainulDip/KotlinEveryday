package coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

interface WithoutRunBlockingCoroutine: CoroutineScope {

//    override val coroutineContext: CoroutineContext
//        get() = Dispatchers.Default

    suspend fun runCoroutine()
}

class WRBTest(override val coroutineContext: CoroutineContext = Dispatchers.Default) : WithoutRunBlockingCoroutine{

    override suspend fun runCoroutine(){
        val job: Job = launch {
            println("Entering Coroutine World Without runBlocking")
        }
        job.join()
    }
}

suspend fun main() {
    WRBTest().runCoroutine()
}