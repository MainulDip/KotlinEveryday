package tasks

import contributors.*
import kotlinx.coroutines.*

suspend fun loadContributorsProgress(
    service: GitHubService,
    req: RequestData,
    updateResults: suspend (List<User>, completed: Boolean) -> Unit
) = coroutineScope {
    val repos = service
        .getOrgRepos(req.org)
        .also { logRepos(req, it) }
        .bodyList()

//    val deferred = repos.map { repo ->
//        async(Dispatchers.Default) {
//            service.getRepoContributors(req.org, repo.name)
//                .also { logUsers(repo, it) }
//                .bodyList()
//        }
//    }
//    val results = deferred.awaitAll().flatten().aggregate()
//    updateResults(results, true)

    var allUsers = emptyList<User>()

    for ((index, repo) in repos.withIndex()) {
        val job = launch {
            val users = service.getRepoContributors(req.org, repo.name)
                .also { logUsers(repo, it) }
                .bodyList()

            allUsers = (allUsers + users).aggregate()
            updateResults(allUsers, index == repos.lastIndex)
        }

//        job.join()
//        updateResults(allUsers, true)
    }

}
