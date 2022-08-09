package generics

fun main() {
    nothing(7,4)
}

fun nothing(x: Int, y: Int): Nothing {
    val exception1 = Exception("For $x : throw exception is the only Nothing Type")
    val exception2 = Exception("For $y : throw exception is the only Nothing Type")
    if (x > y) throw exception1 else throw exception2
}