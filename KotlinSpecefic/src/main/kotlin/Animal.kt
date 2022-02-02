abstract class Animal (
//    define constructors here
    private val name: String,
    private val legs: Int = 4
) {
    // init will run first when instantiated
    init{
        println("Hello my $name")
    }

//  use abstract to force inherited class define their own implementation
    abstract fun makeSound()
}