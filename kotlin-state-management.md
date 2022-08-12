## State management in kotlin (android and native)

### Singleton Pattern:
Kotlin uses object based storage pattern to act like Singleton in Java. Kotlin object can’t have any constructor, but init blocks are allowed if some initialization code is necessary on the fly
```kotlin
object SomeSingleton {
    init {
        println("init complete")
    }
}
```
The object will be instantiated and its init blocks will be executed lazily upon first access, in a thread-safe way. To achieve this, a Kotlin object actually relies on a Java static initialization block. The above Kotlin object will be compiled to the following equivalent Java-code:
```java
public final class SomeSingleton {
   public static final SomeSingleton INSTANCE;

   private SomeSingleton() {
      INSTANCE = (SomeSingleton)this;
      System.out.println("init complete");
   }

   static {
      new SomeSingleton();
   }
}
```
> Some More Kotlin SingleTon

```kotlin
fun main() {
    val srcObj = object : Source<Int> {
        var counter = 0
        override fun nextT(): Int {
            return ++counter
        }
    }
    val srcCls = DemoClass()
    val srcObj2 = DemoObject

    // Singleton Output
    println(demo(srcObj).nextT()) // 1
    println(demo(srcObj).nextT()) // 2
    println(srcObj.nextT()) // 3

    // Non-Singleton Output
    println()
    println(demo(srcCls).nextT()) // 1
    println(demo(srcCls).nextT()) // 2
    println(DemoClass().nextT()) // 1

    // Singleton Output
    println()
    println(demo(srcObj2).nextT()) // 1
    println(demo(srcObj2).nextT()) // 2
    println(srcObj2.nextT()) // 3
    println(DemoObject.nextT()) // 4
    println()
}

interface Source<out T> {
    fun nextT(): T // for <out T> mention "fun nextT(x: T): T" it will throw error - "Type parameter T is declared as 'out' but occurs in 'in' position in type T". // Cannot return the same type
    // for "fun nextT(x: Int): T", anything other than T inside the parameter will work just fine.
}

fun demo(x: Source<Int>): Source<Any> {
//    val objects: Source<Any> = x // This is OK, since T is an out-parameter
//    return objects
    return x
}

interface TestCompare<in T> {
    fun testCompareTo(other: T): Int // T instead of Int (anything other than T) will throw an error "Type parameter T is declared as 'in' but occurs in 'out' position in type T"
}

fun demo2(x: TestCompare<Number>): String {
    x.testCompareTo(1.0) // 1.0 has type Double, which is a subtype of Number
    // Thus, you can assign x to a variable of type Comparable<Double>
    val y: TestCompare<Double> = x // OK!
    return "Test compare"
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
```
### Android State Management:
```kotlin
@Composable
fun Counter(){
    var counter by remember { // delegations, delegated property
        mutableStateOf(value = 0)
    }
    Button(onClick = { counter++ }, modifier = Modifier.padding(top = 77.dp, start = 27.dp)) {
        Text(text = "It's been clicked $counter times")
    }
}
```