## Kotlin OOP Part 2 Overview:
Continued From oop-tour-kotlin.md. This markdow file provides mini docs for following topics:
- [Companio Object | Static Method (Java)](#companion-static)
- [Primary & secondary contructors](#primary-secondary-constructor)
- [Invariance, Covariance, Contravariance](#generic-variances)
- [Generics Variance (in/out) or variance annotation](#generics-in-out)
- [Star Projection (Generics/Foo<*>):](#star-projection)

### <a name="#companion-static"> java/kotlin Static Method || companion object: </a>
```java
class Foo {
  public static int a() { return 1; }
}

Foo.a();
```
```kotlin
class Foo {
  companion object {
     fun a() : Int = 1
  }
}
Foo.a();
// Foo.Companion.a(); // access in java code
```


<details>
<summary>Static Methos Other Ways</summary>


> @JavaStatic annotation to use as normal static method inside java and kotlin all togather

```kotlin
class Foo {
  companion object {
    @JvmStatic
    fun a() : Int = 1;
  }
}
Foo.a() // from kotlin
Foo.a() // from java
```

> use "companion object name" signature other way of the @JavaStatic

```kotlin
class Foo {
  companion object Blah {
    fun a() : Int = 1;
  }
}
```

</details>

### <a name="#primary-secondary-constructor"> Primary & secondary contructors: </a>
Note: if class has primary constructor, Secondary Constructor needs to be delegated to the primary constructor. The compiler select which constructor to use depending on the (number) supplied parameters.
```kotlin
class Constructors private constructor() {
    init {
        println("Init block")
    }

    constructor(i: Int): this() {
        println("Constructor $i")
    }
}


fun main() {
//     Constructors()
    Constructors(1)
}

// Init block
// Constructor 1
```

```kotlin
fun main() {    
    val myObj = Student("First", 15, 77)
    myObj.printMsg()
    val myObj2 = Student("Second", 72)
    myObj2.printMsgOnlyPrimaryConstructor()
    // omiting first param
    val myObj3 = Student(height = 72)
    myObj3.printMsgOnlyPrimaryConstructor()
}
 
class Student(var name: String="Default", height: Int) {
    val height = height
    var age: Int = 14
    constructor (value: String, age: Int, vb: Int): this(value, vb){
        this.age = age
    }
    fun printMsg(){
        println("Calling from Secondary Constructor => Name is $name. Age is $age. Height is $height");
    }
    fun printMsgOnlyPrimaryConstructor(){
        println("Primary Constructor => Name is $name, Height is ${this.height}");
    }
}

// Calling from Secondary Constructor => Name is First. Age is 15. Height is 77
// Primary Constructor => Name is Second, Height is 72
// Primary Constructor => Name is Default, Height is 72
``` 

### <a name="#generic-variances"> Invariance, Covariance, Contravariance: </a>
- Invariance: A generic class is called invariant on the type parameter when for two different types A and B, Class<A> is neither a subtype nor a supertype of Class<B>

- Covariance (subtyping relation): A generic class is called covariant on the type parameter when the following holds: Class<A> is a subtype of Class<B>


- Contravariance (Supertype relation or reverse Covariance): Contravariance describes a relationship between two sets of types where they subtype in opposite directions (Supertype)
```kotlin
open class A
open class B : A()

class Box<in T> {
    private val items = mutableListOf<T>()
    fun deposit(item: T) = items.add(item)
}

//  B is a subtype of A, whereas…
// Box<B> is a supertype of Box<A>
// This happened because of the variance annotation "in"
// Box<A> is applicable anywhere that the code expects Box<B>
```
### <a name="#generics-in-out"> Generics Variance (in/out) or variance annotation: </a>
 - out: When type parameter is to only returned (produced/out) from members of Source<T>, and never consumed.
```kotlin
 interface Source<out T> {
    fun nextT(): T
}

fun demo(strs: Source<String>) {
    val objects: Source<Any> = strs // This is OK, since T is an out-parameter
    // ...
}

fun copy(from: Array<out Any>, to: Array<Any>) { ... }
// here the "from" parameter needs to be returned


// For Foo<out T : TUpper>, where T is a covariant type parameter with the upper bound TUpper.
```

 - in: It makes a type parameter contravariant, meaning it can only be consumed(in) and never produced. Also Array<in String> corresponds to Java's Array<? super String>
```kotlin

interface Comparable<in T> {
    operator fun compareTo(other: T): Int
}

fun demo(x: Comparable<Number>) {
    x.compareTo(1.0) // 1.0 has type Double, which is a subtype of Number
    // Thus, you can assign x to a variable of type Comparable<Double>
    val y: Comparable<Double> = x // OK!
}

// can pass an array of CharSequence or an array of Object to the fill() function
fun fill(dest: Array<in String>, value: String) { ... }
```

### <a name="#star-projection"> Star Projection (Generics): </a>
- Function<*, String> means Function<in Nothing, String>.
- Function<Int, *> means Function<Int, out Any?>.
- Function<*, *> means Function<in Nothing, out Any?>.
https://typealias.com/guides/star-projections-and-how-they-work/