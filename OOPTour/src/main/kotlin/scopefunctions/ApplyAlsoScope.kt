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
    
}