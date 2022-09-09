package higherorderfunctions

class Order(val lines: List<OrderLine>)
class OrderLine(val name: String, val price: Int)

fun mapTester(){
    val order = Order(
        listOf(OrderLine("Tomato", 2), OrderLine("Garlic", 3), OrderLine("Chives", 2))
    )
    val names = order.lines.map { it.name }

    assertThat(names).containsExactly("Tomato", "Garlic", "Chives")
}

fun main() {
    println("Bismillah")
}