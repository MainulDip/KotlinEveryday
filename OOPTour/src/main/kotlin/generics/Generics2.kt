package generics

open class Cat
open class Dog

fun addDog(list: MutableList<Any>) {
    list.add(Dog())
}

fun main() {
    val cats = mutableListOf<Any>(
        Cat(),
        Cat(),
        Cat()
    )

    addDog(cats) // technically ok because Cat extends Any, but MutableList is invariant on its type parameter

    cats.forEach {
        println(it)
    }

    /* if MutableList would not be invariant on its type parameter, you
     * would receive a ClassCastException at runtime
     * since Dog cannot be cast to Cat
     */
}


//fun main() {
//    nothing(7,4)
//}
//
//fun nothing(x: Int, y: Int): Nothing {
//    val exception1 = Exception("For $x : throw exception is the only Nothing Type")
//    val exception2 = Exception("For $y : throw exception is the only Nothing Type")
//    if (x > y) throw exception1 else throw exception2
//}