package interfaces


fun main() {
    // Testing without class's native parameters
    val p = PropertiesDemo()
    println("${p.a} ${p.b}") // 7000 Property Overridden
    println()

    // Testing overriding interface property inside class constructor along with class's native parameters
    val p2 = PropertiesDemo2(1234, "Hello", true, 7)
    println(p2.toString()) // x = 1234, y = Hello, c = true a = 7, b = Again
    println()

    // Different Way, Testing overriding interface property inside class body, and mapping parameters
    val p3 = PropertiesDemo3(4321, "World", true)
    println(p3.toString()) // x = 4321, y = World, c = true a = 44, b = World
}

interface InterfaceProperties {
    val a : Int
    val b : String
        get() = "Hello"
}

// Interface parameters can be override inside both class constructor and class body
class PropertiesDemo : InterfaceProperties {
    override val a : Int = 7000
    override val b : String = "Property Overridden"
}

// Overriding interface properties inside class constructor along with class's native parameters
class PropertiesDemo2(var x: Int, var y: String, var c: Boolean, override val a: Int = 77, override val b: String = "Again"): InterfaceProperties {
    override fun toString(): String {
//        return "x = ${this.x}, y = ${this.y}, c = ${this.c} a = $a, b = $b"
        return "x = $x, y = $y, c = $c a = $a, b = $b"
    }
}

class PropertiesDemo3(var x: Int, var y: String, var c: Boolean): InterfaceProperties {
    // Overriding interface properties inside class body
    override var a: Int = 44 // val/var from interface can be changed
    override val b: String = y
    override fun toString(): String {
//        return "x = ${this.x}, y = ${this.y}, c = ${this.c} a = $a, b = $b"
        return "x = $x, y = $y, c = $c a = $a, b = $b"
    }
}