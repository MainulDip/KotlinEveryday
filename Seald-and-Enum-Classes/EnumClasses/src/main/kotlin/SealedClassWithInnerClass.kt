package org.example

sealed class LatestNewsUiState {
    data class Success(val news: List<String>): LatestNewsUiState()
    data class Error(val exception: String): LatestNewsUiState()
}

fun handleResponse(call: LatestNewsUiState) {
    when (call) {
        is LatestNewsUiState.Error -> println(call.exception)
        is LatestNewsUiState.Success -> println(call.news.joinToString(", "))
        is Processing -> println(call.status)
    }
}

fun main() {
    val networkCallProcessing = Processing()
    handleResponse(networkCallProcessing)

    val netWorkCallSuccess = LatestNewsUiState.Success(listOf("News One", "News Two", "News Three", "News Four"))
    handleResponse(netWorkCallSuccess)

    val newWorkCallError = LatestNewsUiState.Error("Bad Request")
    handleResponse(newWorkCallError)

    println("Type Checking with `is`=> 7 is an Int: ${7 is Int}")

    asChecking(12)
    asChecking("12")
}

class Processing: LatestNewsUiState() {
    val status = "Network call is being processed now"
}

fun asChecking(y: Any){
    // val str: String? = y as String? // it's nullable (either null or string), but if casting fails (Int to String), it will throw a runtime exception
    val str2: String? = y as? String // same, but if casting fails, null will be assigned, no runtime error will happen, hence `as? is safe cast`
    println("$str $str2")
}

