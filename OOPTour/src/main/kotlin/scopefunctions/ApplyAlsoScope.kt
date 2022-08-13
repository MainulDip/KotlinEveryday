package scopefunctions

data class SomePerson(var name: String, var age: Int = 0, var city: String = "")

fun main() {
    // apply scope
    val adam = SomePerson("Adam").apply {
        age = 32
        city = "London"
    }
    println(adam)

    // also scope
    val numbers = mutableListOf("one", "two", "three")
    numbers
        .also {
            println("The list elements before adding new one: $it")
            it.add("four") // add() will return boolean, but "also" will return the object
        }.also {
            println("After adding the number object is = $it")
        }
    // The list elements before adding new one: [one, two, three]
    // After adding the number object is = [one, two, three, four]
}