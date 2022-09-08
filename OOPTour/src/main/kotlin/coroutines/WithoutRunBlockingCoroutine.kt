package coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

interface WithoutRunBlockingCoroutine: CoroutineScope {

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
        val job = launch {
            println("This will not print if the caller \"main\" function is not suspend")
        }

        job.job
        println("Hi")
    }
}

@OptIn(DelicateCoroutinesApi::class)
class ThirdTestWithoutRunBlockingAndInterface {

    init {
        val job = GlobalScope.launch {
            println("Without Implementing CoroutineScope Interface, annotation is suggested by IDE")
        }

        job.job
        println("Hi")
    }
}

suspend fun main() {
    WRBTest().runCoroutine()
    SecondTestWithoutRunBlockingAndInterface() // construction will call init
    ThirdTestWithoutRunBlockingAndInterface()
}