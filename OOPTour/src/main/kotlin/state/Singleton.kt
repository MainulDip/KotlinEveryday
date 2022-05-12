package state

fun main(args: Array<String>)
{
    println(Singleton.name)
    println(Singleton.counter)
    println("The answer of addition is ${Singleton.add(3,2)}")
    println(Singleton.counter)
    println("The answer of addition is ${Singleton.add(10,15)}")
    println(Singleton.counter)
}

object Singleton {
    init {
        println("Singleton class invoked.")
    }

    var name = "Singleton Is Best"
    var counter = 0

    fun add(num1:Int, num2:Int): Int {
        counter++
        return num1.plus(num2)
    }
}