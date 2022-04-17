fun main(args: Array<String>) {
    println("Again Good")

    val theHouse = House(7, "White", true)
}

class House constructor(room : Int, color: String, isForRent : Boolean = false ){
    var value = "${room.toString()} is $color color"

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