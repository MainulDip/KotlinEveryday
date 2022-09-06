package tasks

import contributors.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

fun loadContributorsCallbacks(service: GitHubService, req: RequestData, updateResults: (List<User>) -> Unit) {
    service.getOrgReposCall(req.org).onResponse { responseRepos ->
        logRepos(req, responseRepos)
        val repos = responseRepos.bodyList()
        val allUsers = mutableListOf<User>()
        val countDownLatch = CountDownLatch(repos.size)
            for ((index, repo) in repos.withIndex()) {
                    service.getRepoContributorsCall(req.org, repo.name).onResponse { responseUsers ->
                        logUsers(repo, responseUsers)
                        val users = responseUsers.bodyList()
                        allUsers += users
                        countDownLatch.countDown()
                    }

            }


        // TODO: Why this code doesn't work? How to fix that?
//        println("Hello")
//        updateResults(allUsers.aggregate() as List<User>)
        countDownLatch.await()
        updateResults(allUsers.aggregate())

    }
}

inline fun <T> Call<T>.onResponse(crossinline callback: (Response<T>) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            callback(response)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            log.error("Call failed", t)
        }
    })
}
