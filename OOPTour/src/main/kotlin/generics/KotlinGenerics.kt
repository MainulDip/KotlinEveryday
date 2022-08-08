package generics

fun main() {
    val srcObj = object : Source<Int> {
        var counter = 0
        override fun nextT(): Int {
            return ++counter
        }
    }
    val srcCls = DemoClass()
    val srcObj2 = DemoObject

    println(demo(srcObj).nextT()) // 1
    println(demo(srcObj).nextT()) // 2
    println(srcObj.nextT()) // 3

    println()
    println(demo(srcCls).nextT()) // 1
    println(demo(srcCls).nextT()) // 2
    println(DemoClass().nextT()) // 1

    println()
    println(demo(srcObj2).nextT()) // 1
    println(demo(srcObj2).nextT()) // 2
    println(srcObj2.nextT()) // 3
    println(DemoObject.nextT()) // 4
    println()

//  in generics variance
//    val srcIn = object :




//    val p = PropertiesDemo2(77, "Bismillah", true, 44)
//    println(p.toString())
}

interface Source<out T> {
    fun nextT(): T
}

fun demo(x: Source<Int>): Source<Any> {
//    val objects: Source<Any> = strs // This is OK, since T is an out-parameter
//    return objects
    return x
}

interface TestCompare<in T> {
    fun testCompareTo(other: T): Int
}

fun demo2(x: Comparable<Number>) {
    x.compareTo(1.0) // 1.0 has type Double, which is a subtype of Number
    // Thus, you can assign x to a variable of type Comparable<Double>
    val y: Comparable<Double> = x // OK!
}

// Class implementing interface param
class DemoClass () : Source<Int> {
    var counter = 0
    override fun nextT(): Int {
        return ++counter
    }
}
// Object implementing interface param
object DemoObject : Source<Int> {
    var counter = 0
    override fun nextT(): Int {
        return ++counter
    }
}

interface InterfaceProperties {
    val a : Int
    val b : String
        get() = "Hello"
}

//class PropertiesDemo : InterfaceProperties {
//    override val a : Int = 5000
//    override val b : String = "Property Overridden"
//}

class PropertiesDemo2(x: Int, y: String, c: Boolean, override val a: Int = 77, override val b: String = "Good"): InterfaceProperties {
    val x = x
    override fun toString(): String {
//        return "x = $this.x, y = $this.y, c = $this.c a = $a, b = $b"
//        return "Hello PropertiesDemo2"
        return "Hello $a ${this.b} $x"
    }
}

class RegularClass(private var str: String){
    fun printStr(): String {
        return "SomeClass Param 1 is str = $str"
    }
}

data class DataClass(val str: String){
    fun printStr(): String {
        return "SomeClass Param 1 is str = $str"
    }
}