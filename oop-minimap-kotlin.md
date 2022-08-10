## Kotlin Classes Mini Map
Lets have a quick look of the class, open, nested, inner, inline, abstract, interface, data, sealed, enum Object and Companion Object
```kotlin
// class and open class :
// https://kotlinlang.org/docs/classes.html

class Person { /*...*/ } // empty public constructor ( by default )
class Person(firstName: String) { /*...*/ } // single var public constructor
class Person private constructor(firstName: String) { /*...*/ } // private constructor

class Person(val name: String) { // primary constructor
    val children: MutableList<Person> = mutableListOf()
    init {
        println("Init block will be called when instantiated or by constructor call")
    }
    constructor(name: String, parent: Person) : this(name) { // secondary constructor. Delegation of the primary constructor
        parent.children.add(this)
    }
}

class DontCreateMe private constructor () { /*...*/ } // stop public contructor with non-default visibility and empty parenthesis
```

### open class and method/s, abstract inheritance, primary/secondary constructor, init block: 
```kotlin
open class Polygon {
    open fun draw() { // overriding is optional, not required
        println("Draw Method Of the open Polygon class")
    }
}

class Test : Polygon() {
    init {
        println("Calling init block of the Test class")
    }
    
    // for custom behavour, override the draw method. Otherwise as is
}

abstract class AbstractFromPolygon : Polygon() {
    // Classes that inherit AbstractFromPolygon need to provide their own
    // draw method instead of using the default on Polygon
    abstract override fun draw() // overriding is required
}

class TestingOpen(): AbstractFromPolygon() {
    init {
        println("From init")
    }
    
    override fun draw() {
        println("Abstract ")
    }
}

class TestingPrivateOpen private constructor(): AbstractFromPolygon() {
    init {
        println("Hello")
    }
    
    constructor(i: Int): this() {
    	println("Secondary Calling Int = $i")    
    }
    
    override fun draw() { // required override because of the abstract inheritance
        println("Good From Secondary")
    }
}


fun main() {
    val p = Test()
    p.draw()
    
    val t = TestingOpen() // Hello //
    t.draw()
    
    val s = TestingPrivateOpen(7)
    s.draw()
}

// Calling init block of the Test class
// Draw Method Of the open Polygon class
// From init
// Abstract 
// Hello
// Secondary Calling Int = 7
// Good From Secondary
```

### Abstract Class With Non-Abstract Property (No instantiation allowed):
```kotlin
//abstract class
abstract class Employee(val name: String,val experience: Int) {  // Non-Abstract Properties
    // Abstract Property (Must be overridden by Subclasses)
    abstract var salary: Double
      
    // Abstract Methods (Must be implemented by Subclasses)
    abstract fun dateOfBirth(date:String)
  
    // Non-Abstract Method
    fun employeeDetails() {
        println("Name of the employee: $name")
        println("Experience in years: $experience")
        println("Annual Salary: $salary")
    }
}
// derived class
class Engineer(name: String, experience: Int) : Employee(name,experience) {
    override var salary = 500000.00
    override fun dateOfBirth(date:String){
        println("Date of Birth is: $date")
    }
}
fun main(args: Array<String>) {
    val eng = Engineer("Praveen",2)
    eng.employeeDetails()
    eng.dateOfBirth("02 December 1994")
}

// Output
// Name of the employee: Praveen
// Experience in years: 2
// Annual Salary: 500000.0
// Date of Birth is: 02 December 1994
```
### Interfaces, No instantiation allowed:
Interfaces cannot have constructors in Kotlin. And it cannot have non-abstract methods and properties (abstract class can have both abstract and not-abstract methods and properties). But it can have 
- declarations of abstract methods
- method implementations
- abstract properties
- properties which provide accessor implementations

#### Note: If there is implementation of method or accessors in the interface, overriding is optional in derived/inherited class/interface. So for non-implemented properties and methods, it is required to override

#### Note: For inhering interface, we can not use parenthesis as interface han no constructor
```kotlin
interface Named {
    val name: String
    val AccessorOfName: String get() = "AccessorOfName = $name" // 
    val AccessorOfProp: String get() = "Simple property" // 
    
    val OptionalOverrideImpl: String get() = "This is optional"
}

interface Person : Named {
    val firstName: String
    val lastName: String

    override val name: String get() = "$firstName and $lastName"
    
    // Optional Override
    override val OptionalOverrideImpl: String get() = "This is optional override"
}

data class Employee(
    // implementing 'name' is not required as it has been override/implemented already in Person interface
    override val firstName: String,
    override val lastName: String,
    val position: Int
) : Person


fun main() {
	val em = Employee("a", "b", 7)
    println(em.firstName)
    println(em.name)
    println(em.AccessorOfName)
    println(em.AccessorOfProp)
    println(em.OptionalOverrideImpl)
}

// a
// a and b
// AccessorOfName = a and b
// Simple property
// This is optional override

```


```kotlin

// nested

// inner

// inline

// abstract

// interface

// enum

// sealed

// data

// object

// Companion
```

### Primary & secondary contructors:
Note: if class has primary constructor, Secondary Constructor needs to delegate to the primary constructor. The compiler select which constructor to use depending on the (number) supplied parameters.
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