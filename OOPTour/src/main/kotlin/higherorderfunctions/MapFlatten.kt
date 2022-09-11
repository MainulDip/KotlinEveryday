package higherorderfunctions

data class Order(val lines: List<OrderLine>)
data class OrderLine(val name: String, val price: Int)

fun mapFlattenTester(){
    val order : Order = Order(
        lines = listOf(OrderLine("Tomato", 2), OrderLine("Garlic", 3), OrderLine("Chives", 2))
    )

    val orderLines = order.lines.map { (name, price) -> listOf(name, price) }
    println(orderLines) // [[Tomato, 2], [Garlic, 3], [Chives, 2]]
    println(orderLines.flatten()) // [Tomato, 2, Garlic, 3, Chives, 2]

    // List Data modified
    val modifiedOrders = order.lines.map {(name, price) -> OrderLine("Mod$name", price + 7) }
    println(modifiedOrders)
    val flattenModifiedOrders = modifiedOrders.map { listOf(it.name, it.price) }
    println(flattenModifiedOrders) // [[ModTomato, 9], [ModGarlic, 10], [ModChives, 9]]
    println(flattenModifiedOrders.flatten()) // [ModTomato, 9, ModGarlic, 10, ModChives, 9]
}

fun mapFalttenTesting(){
    val orders: List<Order> = listOf(
        Order(lines = listOf(OrderLine("Garlic", 1), OrderLine("Chives", 2))),
        Order(lines = listOf(OrderLine("Tomato", 1), OrderLine("Garlic", 2))),
        Order(lines = listOf(OrderLine("Potato", 1), OrderLine("Chives", 2))),
    )

    val mapOrder = orders.map { it.lines.map { it.name }}
    println(mapOrder) // [[Garlic, Chives], [Tomato, Garlic], [Potato, Chives]]
    println(mapOrder.flatten())
    // here flatten() is working because now val - mapOrder is no longer the data type Orders/OrderLines, it's now only List<List<String>

    val flatMapOrder = orders.flatMap { it.lines.map { it.name } }
    println(flatMapOrder) // [Garlic, Chives, Tomato, Garlic, Potato, Chives]

    val flatMapOrderList = orders.flatMap { it.lines.map { listOf(it.name, it.price) } }
    println("flatMapOrderList")
    println(flatMapOrderList) // [[Garlic, 1], [Chives, 2], [Tomato, 1], [Garlic, 2], [Potato, 1], [Chives, 2]]
    println(flatMapOrderList.flatten()) // [Garlic, 1, Chives, 2, Tomato, 1, Garlic, 2, Potato, 1, Chives, 2]
}


fun main() {
    mapFlattenTester()
    mapFalttenTesting()
}

// flatten uses
fun useCaseFlatten(){
    val someArray = arrayOf(
        arrayOf(1),
        arrayOf(2, 3),
        arrayOf(4, 5, 6, "7"))
    // Convert all multiDimensionalArray to Single Dimensional List
    val flattenTest = someArray.flatten() //
    println(flattenTest) // [1, 2, 3, 4, 5, 6]

//        val someMore = orders.flatten
    val deepList = listOf(listOf(1), listOf(2, 3, "77"), listOf(4, 7, 6))
    println(deepList.flatten()) // [1, 2, 3, 77, 4, 7, 6]

    val flattenNotWorking = listOf(listOf(1), listOf(2, 3, "77"), listOf(4, 5, 6), OrderLine("Tomato", 2)) // will not work because of OrderLine insertion
    val makeFlattenNotWorking = listOf(listOf(1), listOf(2, 3, "77"), listOf(4, 5, 6), OrderLine("Tomato", 2).let { listOf(it.name, it.price) }) // make flatten work by converting to listOf()
    println(makeFlattenNotWorking.flatten())

    val deepList2 = listOf(listOf(1), listOf(2, 3, listOf("44","77", 7)), listOf(4, 7, 6))
    println(deepList2.flatten())
}