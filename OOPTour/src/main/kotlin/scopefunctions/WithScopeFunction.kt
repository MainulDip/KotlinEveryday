package scopefunctions

fun main() {

    val numbers = mutableListOf("one", "two", "three")
    val returnedValue = with(numbers) {
        println("'with' is called with argument $this")
        println("It contains $size elements")
    }
    // storing/returning calculated value inside helper object "val firstAndLast"
    val firstAndLast = with(numbers) {
        "This string will be returned : " +
        "The first element is ${first()}," +
                " the last element is ${last()}"
    }
    println(firstAndLast)
}