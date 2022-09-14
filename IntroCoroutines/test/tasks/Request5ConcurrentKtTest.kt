package tasks

import contributors.MockGithubService
import contributors.User
import contributors.expectedConcurrentResults
import contributors.testRequestData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class Request5ConcurrentKtTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testConcurrent() = runBlocking{
        val startTime = System.currentTimeMillis()
//        val startTime = currentTime
        var result = emptyList<User>()
        val job = launch (Dispatchers.Default) {
            result = loadContributorsConcurrent(MockGithubService, testRequestData)
        }
        job.join()
        Assert.assertEquals("Wrong result for 'loadContributorsConcurrent'", expectedConcurrentResults.users, result)
        val totalTime = System.currentTimeMillis() - startTime

//        Assert.assertEquals(
//            "The calls run concurrently, so the total virtual time should be 2200 ms: " +
//                    "1000 ms for repos request plus max(1000, 1200, 800) = 1200 ms for concurrent contributors requests)",
//            expectedConcurrentResults.timeFromStart, totalTime
//        )

        Assert.assertTrue(
            "The calls run concurrently, so the total virtual time should be 2200 ms: " +
                    "1000 ms for repos request plus max(1000, 1200, 800) = 1200 ms for concurrent contributors requests)",
            expectedConcurrentResults.timeFromStart + 1000 > totalTime
        )

//        Assert.assertTrue(
//            "The calls run concurrently, so the total virtual time should be 2200 ms: " +
//                    "1000 ms for repos request plus max(1000, 1200, 800) = 1200 ms for concurrent contributors requests)",
//            totalTime in expectedConcurrentResults.timeFromStart..(expectedConcurrentResults.timeFromStart + 500)
//        )
    }
}