package coroutines

import kotlinx.coroutines.*
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

class SecondTestWithoutRunBlockingAndInterface(): CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    init {
        val jobS = launch {
            println("Second Test Without RunBlocking from single class")
        }

        jobS.job
        println("Hi")
    }

//    suspend fun joinJob(job: Job){
//        job.join()
//    }
}

suspend fun main() {
//    WRBTest().runCoroutine()
    SecondTestWithoutRunBlockingAndInterface()
}