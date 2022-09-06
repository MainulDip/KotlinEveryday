package asynchronousprogramming

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CompletableFutureClient {
    private val jdkHttpClient: HttpClient = HttpClient.newHttpClient()
    private val jMapper = jacksonObjectMapper()

    fun fetchMostRecentOrderId(): String {
        val requestFuture: CompletableFuture<String> = jdkHttpClient.sendAsync(
            HttpRequest.newBuilder(URI.create("https://httpbin.org/uuid"))
                .GET()
                .build(), HttpResponse.BodyHandlers.ofString()
        ).thenApply { it.body() }

        val response = requestFuture.get()
        val resStr = jMapper.readValue<Order>(response)

        for (i in 1..1000000) {
            println("Printing 1000 times")
        }

        return resStr.uuid
    }

    fun fetchDeliveryCost(orderId: String): CompletableFuture<String> {
        return fetchDelayedData(orderId, "Delivery cost")
    }

    fun fetchStockInformation(orderId: String): CompletableFuture<String> {
        return fetchDelayedData(orderId, "Stock")
    }

    private fun fetchDelayedData(orderId: String, operation: String): CompletableFuture<String> {
        val fullName = "jdkHttpClient($operation)"
//        logDelayedRequest(fullName)

        return jdkHttpClient.sendAsync(
            HttpRequest.newBuilder(URI.create("https://httpbin.org/delay/1"))
                .POST(HttpRequest.BodyPublishers.ofString(delayedOperationRequest(operation, orderId)))
                .build(), HttpResponse.BodyHandlers.ofString()
        ).thenApply { it.body() }
    }

}

fun delayedOperationRequest(operation: String, orderId: String): String {
    val randomInt = (0..10).random()
    return "$operation of order '$orderId': $randomInt"
}


data class Order(val uuid: String)

fun main() {
    val cfc: CompletableFutureClient = CompletableFutureClient();
    var remoteOrder: String = ""
//    val jsonResult = jacksonObjectMapper().readValue<jsonObj>(remoteReq.fetchMostRecentOrderId().get())
//    println(remoteReq.fetchMostRecentOrderId().get())
    val executorService: ExecutorService = Executors.newFixedThreadPool(1)
    executorService.execute{
        remoteOrder = cfc.fetchMostRecentOrderId()
        println("Fetched Id as uuid: $remoteOrder")
        executorService.shutdown()
        if (!executorService.isTerminated) println("Fetched uuid: $remoteOrder")
    }

    println("publishing")

}