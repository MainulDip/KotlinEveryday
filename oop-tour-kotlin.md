## Kotlin Abstract Class
```kt
abstract class Polygon {
    abstract fun draw()
}

class Rectangle : Polygon() {
    override fun draw() {
        // draw the rectangle
    }
}

//You can override a non-abstract open member with an abstract one.

open class Polygon {
    open fun draw() {
        // some default polygon drawing method
    }
}

abstract class WildShape : Polygon() {
    // Classes that inherit WildShape need to provide their own
    // draw method instead of using the default on Polygon
    abstract override fun draw()
}
```


Object declarations: Singleton Pattern
```kt
object DataProviderManager {
    fun registerDataProvider(provider: DataProvider) {}
}
//To refer to the object, use its name directly, no instantiation required voila
DataProviderManager.registerDataProvider(...)

//Such objects can have supertypes

object DefaultListener : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) { ... }

    override fun mouseEntered(e: MouseEvent) { ... }
}
// overriding methods
```
### Companion objects (inside class): Java static method + some more
> If declared inside of a class, it can access its members / internals-of-the-class (such as a factory method) using only the class name as a qualifier, without instantiation like static method (But not exactly). 
```kt
class MyClass {
    companion object Factory {
        fun create(): MyClass = MyClass()
    }
}

val f = MyClass.Factory()

class MyClass1 {
    companion object Named { }
}

val x = MyClass1

class MyClass2 {
    companion object { }
}

val y = MyClass2


// unlike static method in java, companion object can implement interfaces (unless @JvmStatic annotation)

interface Factory<T> {
    fun create(): T
}

class MyClass {
    companion object : Factory<MyClass> {
        override fun create(): MyClass = MyClass()
    }
}

val f: Factory<MyClass> = MyClass
```

### Inheritance
Hit on https://kotlinlang.org/docs/inheritance.html
```kt
class Example // Implicitly inherits from Any, and Any has three methods: equals(), hashCode(), and toString()
```
> Classes are final by default, To make a class inheritable, mark it with the open keyword. 

```kt
open class Base(p: Int){}
class Derived(p: Int) : Base(p){}
```

### Interface


### Data Class

### Sealed Class

### Extension Class

### Enum Class
### Inline Class