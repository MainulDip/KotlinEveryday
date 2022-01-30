## Kotlin Walk through
> Main.kt | kotlin program starts from this, like index.js/php

### function
```kt
fun main() {
    println("Hello World")

//    Note: var and val
    var x: Int = 3 // 3L will be 64bit (Long)

    println("This value is $x")
    x = 4
    println("But The Value is now $x")
    val y: Int = 7 // val is constant
    print("Hello again $y")

//    types: 3 | 3L | 3.00 (double) | 3.00f (float)

    val wholeNumber: Int = 3
    val bigNumber: Long = 3L
    val preciseDecimal: Double = 3.33 // takes more memory
    val decimal: Float = 3.33f
    val checkBoolean: Boolean = false
    val someString: String = "hello world"
    val singleLetter: Char  = 'a' //
}
```

### Arithmetic Oparators:
> Oparators for mathmetical operation like + | - | * | / | %
```kt
val m = 3 * 4
println("The value of multiply (m) is $m")
val d = 3/4 // rerun 0 as Integer, will not return float value
println("The value of divide (d) is $d")
val f = 3f / 4f // need to specify float identifier to get the float value
println("The value of float divide (f) is $f")
val r = 10 % 3 // modulus or reminder operator
println("The value of r after modulus operation is $r")
```

### Check and Boolean
```kt
val amIAdult = true
val amIProgrammer = true
val anIAdultProgrammer = amIAdult && amIProgrammer
println("Boolean operation for amIAdultProgrammer = $anIAdultProgrammer")
//  Also || , == , != , !( val1 != val2 )
```

### String and Operations
```kt
val string = "Use double quotes for string and single quote for Char (Single letter)"
println(string.uppercase())
```

### Conditionals
```kt
//  Conditionals
    val conditionVal = 1 + 3
    if( conditionVal == 2 ) {
        println("conditionalVal is 2")
    } else if (conditionVal == 3) {
        println("value is 3")
    } else {
        println ("Neither of the condition is True")
    }

//  functional style
    val conditional2 = if ( conditionVal == 2 + 1 ) 2 else "not a match"
    println("conditional2 value is $conditional2")

//  when conditional, it's like switch style conditional
    
    val checkWhen = 4
    when(checkWhen) {
        in 1..2 -> println("checkWhen is between 1 and 2")
        in 3..10 -> println("checkWhen is between 3 and 10")
        in 11..20 -> println("checkWhen is between 11 and 20")
        else -> {
            println("checkWhen is not in the range above")
        }
    }
```
### Default value and null safety
> ?: , ? , !!
```kt
//  kotlin is null safety language
    val nullValue: Int? = null
    val number1 = readLine() ?: "0" // Assign default value
    val number2 = readLine() ?: "0"
//    val inputResult = number1!!.toInt() + number2!!.toInt()
    val inputResult = number1.toInt() + number2.toInt()
//    val userInput = readLine() // return String type
    println("userInput result is $inputResult")
```

### List
```kt
//  Immutable List
    val shoppingList = listOf<String>("Hello", "World")
    println(shoppingList[0])
    println("shoppingList first value is ${shoppingList[0]}")
//  Mutable List
    val mutableShoppingList = mutableListOf<String>("Mutable", "Hello World")
    mutableShoppingList.add("Good is golden")
    println(mutableShoppingList[0])
    println("mutableShoppingList  value is ${mutableShoppingList[0]} ${mutableShoppingList[mutableShoppingList.size - 1]}")
    println(mutableShoppingList.toString())
```

### Loop
> check: https://kotlinlang.org/docs/control-flow.html#for-loops
```kt
// while loop
    var counter = 0
    while( counter < shoppingList.size) {
        println("immutable shoppingList on loop $counter is ${shoppingList[counter]}")
        counter++
    }

// for loop and range

    for((index, item) in mutableShoppingList.withIndex() ) {
        println("mutable printing from for loop on shoppingList $index is $item")
    }

    for ( number in 1..100){
        println("Outputting range in for loop wher number = $number")
    }
```

### Defining Calling Functions
```kt
fun printNumber(){
    for(i in 1..10){
        println("Calling function printNumber and it returns $i")
    }
}
printNumber()

fun checkIsEven(number: Int): Boolean{
    return number % 2 == 0
}
val evenNum = 7
val checkEven = checkIsEven(evenNum)
println("checkEven for $evenNum is ${if(checkEven) "even" else "\"odd\""}")
val checkEven2 = checkIsEven(number = evenNum) // adds good redability
// Setting Function's Default Args Value
fun defaultArgVal(number: Int = 7): Boolean { return number % 2 == 0}
println(defaultArgVal())
```kt

### Extension Function
```kt
// extension function: extend an already existed type
// call extension function like object function
fun Int.isOdds(): Boolean {
    return this % 2 != 0
}
val oddValue = 3
println(oddValue.isOdds())
```

## Object Oriented Kotlin:
> Define Class On It's Own File

> class can be class, open class, abstract class
```kt
// define as abstract to restrict direct intantiation
// to be inherited class must be open or abstract

abstract class Animal (
//    define constructors here
    private val name: String,
    private val legs: Int = 4
) {
    // init will run first when instantiated
    init{
        println("Hello my $name")
    }

//  use abstract to force inherited class define their own implementation
    abstract fun makeSound()
}

// inheritance from the Animal class
class Dog: Animal(name= "Dog") {
//    can redefine the init function
//    can add additional function also
    fun bark(): String {
//        println("Gheu")
        return "Gheu Gheu Gheu"
    }

//  implementation of the abstract function front Animal abstract class
    override fun makeSound() {
        println("From Override makeSound ${bark()}")
    }
}

class Cat: Animal(name = "Cat") {
    fun meao () {
        println("Meao Meao Meao Meao")
    }
    
//  implementation of the abstract function front Animal abstract class
    override fun makeSound() {
        println("Printing From override function")
        meao()
    }
}

// Call from main.kt
//  val dog = Animal(name = "Dog") // throw error as defined as abstract class
    val dog = Dog()
    dog.bark()
    val cat = Cat()
    cat.meao()
    cat.makeSound()
    dog.makeSound()
```


### Anonymous Class
```kt
//Anonymous class
    val bear = object : Animal(name = "Cow") {
        override fun makeSound() {
            println("Calling From Anonymous class and the nos is \"Humba\"")
        }
    }
    bear.makeSound()
```

### Exception Handling
```kt
val numberExceptionCheck = readLine() ?: "0"
val parsedNumber = try {
    numberExceptionCheck.toInt()
} catch (e: Exception) {
    7
}
println(parsedNumber)
```

### Lambda Function
> Lamda: Functions that are passed as parameter/args of another function