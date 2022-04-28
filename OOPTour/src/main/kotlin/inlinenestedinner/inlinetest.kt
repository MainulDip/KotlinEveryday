package inlinenestedinner

@JvmInline
value class Width(val width: Long)
@JvmInline
value class Height(val height: Long)

class Rect (private val w : Width, private val h: Height){
   fun printDim(){
       // println("w  h = $w  $h")
       // w  h = Width(width=100)  Height(height=70)

       println("w * h = ${w.hashCode() * h.hashCode()}")
       // w * h = 7000
   }
}

fun main() {
    val width = Width(100L)
    val height = Height(70L)

    val shape = Rect(width, height) // IDE will force setting correct Value
    // Rect(height, width) will throw an error because of explicitly using inline class as type
    shape.printDim()
}