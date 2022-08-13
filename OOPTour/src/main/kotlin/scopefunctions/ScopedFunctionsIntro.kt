package scopefunctions

data class Person(var name: String, var age: Int, var city: String) {
    fun moveTo(newCity: String) { city = newCity }
    fun incrementAge() { age++ }
}


fun main() {
    // using Scope Function "let"
    Person("Alice", 20, "Amsterdam").let {
        println(it)
        it.moveTo("London")
        it.incrementAge()
        println(it)
    }

    println()

    // without Scope Function "let"
    val alice = Person("John", 21, "Canada")
    println(alice)
    alice.moveTo("Tokyo")
    alice.incrementAge()
    println(alice)

}