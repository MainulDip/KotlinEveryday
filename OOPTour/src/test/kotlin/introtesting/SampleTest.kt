package introtesting

import kotlinx.coroutines.*
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SampleTest(){
    @Test
    fun myfirstmanualtest() {
        assertEquals(1,1)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testDelayInSuspend() = runBlockingTest {
        val realStartTime = System.currentTimeMillis()
        val virtualStartTime = currentTime

        foo()
        println("${System.currentTimeMillis() - realStartTime} ms") // ~ 6 ms
        println("${currentTime - virtualStartTime} ms")             // 1000 ms

        assertEquals(1,1)
    }

    suspend fun foo() {
        delay(100000)    // auto-advances without delay
        println("foo") // executes eagerly when foo() is called
    }
}

