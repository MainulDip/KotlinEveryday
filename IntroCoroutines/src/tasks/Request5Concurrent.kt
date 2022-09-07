package tasks

import contributors.*
import kotlinx.coroutines.*

suspend fun loadContributorsConcurrent(service: GitHubService, req: RequestData): List<User> = coroutineScope {
    val repos = service
        .getOrgRepos(req.org)
        .also { logRepos(req, it) }
        .bodyList()

    val deferreds: List<Deferred<List<User>>> = repos.map { repo ->
        async(Dispatchers.Default) {
             service.getRepoContributors(req.org, repo.name)
            .also { logUsers(repo, it) }
            .bodyList()
        }
    }
    deferreds.awaitAll().flatten().aggregate() // List<List<User>>

//    repos.flatMap { repo ->
//        service.getRepoContributors(req.org, repo.name)
//            .also { logUsers(repo, it) }
//            .bodyList()
//    }.aggregate()
}