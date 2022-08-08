package generics

fun main() {
//    println("hello")
//    val src = object : Source<String> {
//        override fun nextT(): String {
//            return "hello"
//        }
//    }
//    demo(src)

    val d = demo(object : Source<String> {
        override fun nextT(): String {
            return "hello"
        }
    })

    println(d.nextT())

//    val p = PropertiesDemo2(77, "Bismillah", true, 44)
//    println(p.toString())
}

interface Source<out T> {
    fun nextT(): T
}

fun demo(strs: Source<String>): Source<Any> {
//    val objects: Source<Any> = strs // This is OK, since T is an out-parameter
//    return objects
    return strs
}

interface Comparable<in T> {
    operator fun compareTo(other: T): Int
}

fun demo(x: Comparable<Number>) {
    x.compareTo(1.0) // 1.0 has type Double, which is a subtype of Number
    // Thus, you can assign x to a variable of type Comparable<Double>
    val y: Comparable<Double> = x // OK!
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