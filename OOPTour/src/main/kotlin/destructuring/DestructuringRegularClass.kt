package destructuring

// Destructuring on regular classes need to provide operator function componentN

class Order(val lines: List<OrderLine>)

class OrderLine(val name: String, val price: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderLine

        if (name != other.name) return false
        if (price != other.price) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + price
        return result
    }

    override fun toString(): String {
        return "OrderLine(name='$name', price=$price)"
    }

    operator fun component1(): Any {
        return name
    }

    operator fun component2(): Any {
        return price
    }


}

fun mapTester(){
    val order : Order = Order(
        lines = listOf(OrderLine("Tomato", 2), OrderLine("Garlic", 3), OrderLine("Chives", 2))
    )

    val namesprice = order.lines.map { (name, price) -> "$name : $price" }
    println(namesprice)
//    val namesDestructing = order.lines.map { (name, price) -> name  }
    val totalPrice = order.lines.map { it.price }.sum()
//    println("names: $names && totalPrice: $totalPrice") // names: [Tomato, Garlic, Chives] && totalPrice: 7
}

fun flatMapTesting(){
    val orders: List<Order> = listOf(
        Order(lines = listOf(OrderLine("Garlic", 1), OrderLine("Chives", 2))),
        Order(lines = listOf(OrderLine("Tomato", 1), OrderLine("Garlic", 2))),
        Order(lines = listOf(OrderLine("Potato", 1), OrderLine("Chives", 2))),
    )

//    val mapOrder = orders.map { it.lines.map { OrderLine(it.name, it.price ) } }
//    println(mapOrder.map { it.map { listOf(it.name, it.price) } })

    val mapOrder = orders.map { it.lines.map { it.name }}
    println(mapOrder) // [[Garlic, Chives], [Tomato, Garlic], [Potato, Chives]]

    val flatMapOrder = orders.flatMap { it.lines.map { it.name } }
    println(flatMapOrder) // [Garlic, Chives, Tomato, Garlic, Potato, Chives]

    val someArray = arrayOf(
        arrayOf(1),
        arrayOf(2, 3),
        arrayOf(4, 5, 6, "7"))
    // Convert all multiDimensionalArray to Single Dimensional List
    val flattenTest = someArray.flatten()
    println(flattenTest) // [1, 2, 3, 4, 5, 6]

//        val someMore = orders.flatten
    val deepList = listOf(listOf(1), listOf(2, 3, "77"), listOf(4, 7, 6))
//    val flattenNotWorking = listOf(listOf(1), listOf(2, 3, "77"), listOf(4, 5, 6), OrderLine("Tomato", 2)) // will not work because of OrderLine insertion
    println(deepList.flatten()) // [1, 2, 3, 77, 4, 7, 6]
}

fun main() {
    mapTester()
    flatMapTesting()
}