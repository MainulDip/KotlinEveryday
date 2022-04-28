package inlinenestedinner
class Outer {
    private val bar: Int = 1
    class Nested {
        fun foo() = 2
        //fun baz() = bar // outer class properties not accessible in Nested Class
    }

    inner class Inner {
        fun foo() = bar // accessible in Inner Class
    }
}

fun main() {
    val n = Outer.Nested().foo() // == 2
    val i = Outer().Inner().foo() // == 1
}