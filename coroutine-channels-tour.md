## Overview:
This is from the official "Introduction to Coroutines and Channels" official hands on guide.

https://play.kotlinlang.org/hands-on/Introduction%20to%20Coroutines%20and%20Channels/01_Introduction .

### Task:
First, we'll look at how to implement the logic in a blocking way: easy to read and reason for it, but wrong since it freezes the UI. Then we'll use callbacks to fix it, and compare these solutions with one that uses coroutines.

### App overview:
- On main kt ContributorUI is called with the JFrame inherited methods (pack, setLocalRelativeTo etc) inside scope function "apply" block.

- ContributorUI inherits JFrame and Contributor interface.
- While initializing ContributorUI, the init block calls the init method inherited from Contributors.kt
- Contributors interfaces init function/method (not init{} block) calls the abstract methods addLoadListener, addOnWindowClosingListener, etc (which are defined by overriding inside inherited class ContributorUI).
```kotlin
fun init() {
        // Start a new loading on 'load' click
        addLoadListener { // 
            saveParams()
            loadContributors()
        }

        // Save preferences and exit on closing the window
        addOnWindowClosingListener {
            job.cancel()
            saveParams()
            System.exit(0)
        }

        // Load stored params (user & password values)
        loadInitialParams()
    }
```

- these methods (addLoadListener, addOnWindowClosingListener, etc) call all the necessery methods like loadContributors() which again calls all the necessery methods to make api calls and receive serialized data.
```kotlin
fun loadContributors() {
    val (username, password, org, _) = getParams()
    val req = RequestData(username, password, org)

    clearResults()
    val service = createGitHubService(req.username, req.password)

    val startTime = System.currentTimeMillis()
    when (getSelectedVariant()) {
        BLOCKING -> { // Blocking UI thread
            val users = loadContributorsBlocking(service, req)
            updateResults(users, startTime)
        }
        BACKGROUND -> { // Blocking a background thread
            loadContributorsBackground(service, req) { users ->
                SwingUtilities.invokeLater {
                    updateResults(users, startTime)
                }
            }
        }
        CALLBACKS -> { // Using callbacks
            loadContributorsCallbacks(service, req) { users ->
                SwingUtilities.invokeLater {
                    updateResults(users, startTime)
                }
            }
        }
        SUSPEND -> { // Using coroutines
            launch {
                val users = loadContributorsSuspend(service, req)
                updateResults(users, startTime)
            }.setUpCancellation()
        }
        CONCURRENT -> { // Performing requests concurrently
            launch {
                val users = loadContributorsConcurrent(service, req)
                updateResults(users, startTime)
            }.setUpCancellation()
        }
        NOT_CANCELLABLE -> { // Performing requests in a non-cancellable way
            launch {
                val users = loadContributorsNotCancellable(service, req)
                updateResults(users, startTime)
            }.setUpCancellation()
        }
        PROGRESS -> { // Showing progress
            launch(Dispatchers.Default) {
                loadContributorsProgress(service, req) { users, completed ->
                    withContext(Dispatchers.Main) {
                        updateResults(users, startTime, completed)
                    }
                }
            }.setUpCancellation()
        }
        CHANNELS -> {  // Performing requests concurrently and showing progress
            launch(Dispatchers.Default) {
                loadContributorsChannels(service, req) { users, completed ->
                    withContext(Dispatchers.Main) {
                        updateResults(users, startTime, completed)
                    }
                }
            }.setUpCancellation()
        }
    }
}
```

- createGitHubService method call returns all the other functionality from GithubService.kt