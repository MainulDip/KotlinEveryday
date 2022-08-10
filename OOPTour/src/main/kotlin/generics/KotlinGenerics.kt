package generics
//
//fun main() {
//    val srcObj = object : Source<Int> {
//        var counter = 0
//        override fun nextT(): Int {
//            return ++counter
//        }
//    }
//    val srcCls = DemoClass()
//    val srcObj2 = DemoObject
//
//    // Singleton Output
//    println(demo(srcObj).nextT()) // 1
//    println(demo(srcObj).nextT()) // 2
//    println(srcObj.nextT()) // 3
//
//    // Non-Singleton Output
//    println()
//    println(demo(srcCls).nextT()) // 1
//    println(demo(srcCls).nextT()) // 2
//    println(DemoClass().nextT()) // 1
//
//    // Singleton Output
//    println()
//    println(demo(srcObj2).nextT()) // 1
//    println(demo(srcObj2).nextT()) // 2
//    println(srcObj2.nextT()) // 3
//    println(DemoObject.nextT()) // 4
//    println()
//}
//
//interface Source<out T> {
//    fun nextT(): T // but for "fun nextT(x: T): T" it will throw error - "Type parameter T is declared as 'out' but occurs in 'in' position in type T"
//    // for "fun nextT(x: Int): T", anything other than T inside the parameter will work just fine.
//}
//
//fun demo(x: Source<Int>): Source<Any> {
////    val objects: Source<Any> = x // This is OK, since T is an out-parameter
////    return objects
//    return x
//}
//
//interface TestCompare<in T> {
//    fun testCompareTo(other: T): Int // T instead of Int (anything other than T) will throw an error "Type parameter T is declared as 'in' but occurs in 'out' position in type T"
//}
//
//fun demo2(x: TestCompare<Number>): String {
//    x.testCompareTo(1.0) // 1.0 has type Double, which is a subtype of Number
//    // Thus, you can assign x to a variable of type Comparable<Double>
//    val y: TestCompare<Double> = x // OK!
//    return "Test compare"
//}
//
//// Class implementing interface param
//class DemoClass () : Source<Int> {
//    var counter = 0
//    override fun nextT(): Int {
//        return ++counter
//    }
//}
//
//// Object implementing interface param
//object DemoObject : Source<Int> {
//    var counter = 0
//    override fun nextT(): Int {
//        return ++counter
//    }
//}

fun main() {

    //  Custom Lambda Generic Function
    val lambdaList3 = listOf("Durium", "Mango", "Banana")
    val count3 = lambdaList3.customLamGenericsFunction { value ->
//      in lambda function last line get automatic return, so no need to mention return
        value.length >= 4
    }
    println("Custom-Lambda Generic function return value is $count3")

    // apply generics without lambda
    val count4 = lambdaList3.customLamGenericsFunction(fun (value: String): Boolean {
        return value.length >= 4
    })
    val count5 = lambdaList3.customLamGenericsFunction(fun (value): Boolean = value.length >= 4)
    println("Custom-Lambda Generic function return value is $count4")
    println("Custom-Lambda Generic function return value is $count5")

}

fun <T> List<T>.customLamGenericsFunction(value: (T) -> Boolean): Int {
    var counter = 0
    for (item in this) {
        if(value(item)) {
            counter++
        }
    }
    return counter
}