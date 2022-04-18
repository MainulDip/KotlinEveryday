fun main(args: Array<String>) {
    //println("Again Good")
    House();
    //House(null, null);
    //val theHouse = House(7, "White", true)
}

class House constructor(room : Int?, color: String?, isForRent : Boolean = false ){
    var value = "${room.toString()} is $color color"

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