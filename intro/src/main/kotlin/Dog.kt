// inheritance from the Animal class
class Dog: Animal(name= "Dog") {
//    can redefine the init function
//    can add additional function also
    fun bark(): String {
//        println("Gheu")
        return "Gheu Gheu Gheu"
    }

//  implementation of the abstract function front Animal abstract class
    override fun makeSound() {
        println("From Override makeSound ${bark()}")
    }
}