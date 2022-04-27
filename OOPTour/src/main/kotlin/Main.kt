fun main(args: Array<String>) {
    //println("Again Good")
    val g: House = House();
    println(g.checkingSetters)
    g.checkingSetters = "Voila"
    println(g.checkingSetters)
    //House(null, null);
    //val theHouse = House(7, "White", true)
}

class House constructor(room : Int?, color: String?, isForRent : Boolean = false ){
    var value = "${room.toString()} is $color color"

    var checkingSetters: String = "Hello"
        get() = "$field World"
        set(value) {
            field = "Your Entered Value: $value where prev value was: \$field: $field but \$checkingSetters: $checkingSetters"
            // Your Entered Value: Voila where prev value was: $field: Hello but $checkingSetters: Hello World World
            // accessing checkingSetters will call the getter always, but field will return the real value
        }

    constructor(newVals : String = "", room : Int = 0, color: String = "", isForRent : Boolean = false) : this(room, color, isForRent) {
        println("Empty Constructor testing")
    } // it will be called after primary and all the init blocks.

    init {
        println("init will execute when instantiated")
    }

    init {
        println("Second Init and the house is ${if (isForRent) "available" else "not available"} for rent")
    }

    init {
        println("Third init, visibility modifier implementation in details is in the specific package")
    }



}