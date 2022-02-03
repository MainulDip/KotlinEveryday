import java.time.LocalDate
import java.util.*

fun main() {
    val list = listOf("Appele", "Banana", "Orange")
    println("list: list of $list and first item is ${list[0]}")

    val set = setOf("Mango", "PineApple", "Watermelon")
    println("set: set of $set and first item is ${set.first()} and elementAt(1) ${set.elementAt(1)}")

    val map = mapOf(1 to "Peach", 2 to "Liche", 3 to "Guava")
    println("map: map of $map and first item is ${map[1]}")

    val sequence = sequenceOf("Apple", "Banana", "Orange")
    println("sequence: sequence of ${sequence.forEach { print(it) }} and first item is ${sequence.elementAt(2)}")

    val array = arrayOf("Mango", "PineApple", "Watermelon")
    println("array: array of ${array.forEach { print(it) }} and first item is ${array[1]}")
    println("array: array of ${array.contentToString()} and first item is ${array[1]}")
}

