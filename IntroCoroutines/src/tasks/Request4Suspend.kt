package tasks

import contributors.*
import java.util.concurrent.CountDownLatch

suspend fun loadContributorsSuspend(service: GitHubService, req: RequestData): List<User> {
    val repos = service
        .getOrgRepos(req.org)
        .also { logRepos(req, it) }
        .bodyList()

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

    countDownLatch.await()
    return allUsers
}