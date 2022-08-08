## Kotlin Language Tour:
It's a quick language tour to re-connect with most of the Kotlin language syntaxes and features. When you work with multiple language everyday, it can be a good idea to have a personalized language tour. So lets start
Link: (oop-minimap-kotlin.md)[oop-minimap.kotlin.md]
Link: (oop-tour-kotlin.md)[oop-tour-kotlin.md] 

> Main.kt | kotlin program starts from this (like index.js/php). Also main() function in raw kotlin

### Variables and Types:
```kt
fun main() {
    println("Hello World")

//  Note : var and val
    var x: Int = 3 // 3L will be 64bit (Long)

    println("This value is $x")
    x = 4
    println("But The Value is now $x")
    val y: Int = 7 // val is constant
    print("Hello again $y")

//  types: 3 | 3L | 3.00 (double) | 3.00f (float)

    val wholeNumber: Int = 3
    val bigNumber: Long = 3L
    val preciseDecimal: Double = 3.33 // takes more memory
    val decimal: Float = 3.33f
    val checkBoolean: Boolean = false
    val someString: String = "hello world"
    val singleLetter: Char  = 'a'
    val cars = arrayOf("Volvo", "BMW", "Ford", "Mazda")

    // IntRange
    val diceRange: IntRange = 1..6
    val randomNumber = diceRange.random()
    println("Random number: ${randomNumber}") // Random number: 6

// loop and check

    if ("Volvo" in cars) {
        println("It exists!")
    } else {
    println("It does not exist.")
    }

    for (x in cars) {
        println(x)
    }
}
```

### when block:
When Signature
```kt
when (variable) {
    matches-value -> execute-this-code
    matches-value -> execute-this-code
    else -> if nothing match, execute this
    // if else block is not given, IDE will show "exhaustive" error (it must handle all the cases possible)
}
```
When Uses
```kt
fun main() {
    val myFirstDice = Dice(6)
    val rollResult = myFirstDice.roll()
    val luckyNumber = 4

    when (rollResult) {
        luckyNumber -> println("You won!")
        1 -> println("So sorry! You rolled a 1. Try again!")
        2 -> println("Sadly, you rolled a 2. Try again!")
        3 -> println("Unfortunately, you rolled a 3. Try again!")
        5 -> println("Don't cry! You rolled a 5. Try again!")
        6 -> println("Apologies! You rolled a 6. Try again!")
    }

    // when can also return a value
    val drawableResource = when (diceRoll) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
    diceImage.setImageResource(drawableResource)
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
// Setting Function's Default Args Value.
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
Note: When calling there is no parenthses ()" before arrow notation "->" like defining
> Lamda: Functions that are passed as parameter/args of another function

```kt
fun main(){
    //  Custom Lambda Function
    val lambdaList2 = listOf("apple", "orange", "cherry")
    val count2 = lambdaList2.customLamFunction { value ->
//      in lambda function last line get automatic return, so no need to mention return
        value.length == 6
    }
    println("Custom-Lambda function return value is $count2")
}

fun List<String>.customLamFunction(fn: (String) -> Boolean): Int {
//  useWithLambda Function will receive only one parameter, which is another function (lambda)
//  in param, fn (lambda) function's signature is defined,
//  which will be called here but function's logic will be implemented outside in lambda expression
//  fn function will receive one parameter, which must be String Type
    var counter = 0
    for (item: String in this) {
        if(fn(item)) {
            counter++
        }
    }
    return counter
}
```

### Lambda Type Declaration and Instantiation:
```kt
// Lambda Declaration. Note: IntArray.fold() is a built in function in Kotlin
inline fun <R> IntArray.fold(
    initial: R,
    operation: (acc: R, Int) -> R
): R

// Instantiation or Implementation
val items = listOf(1, 2, 3, 4, 5)

// Lambdas are code blocks enclosed in curly braces.
items.fold(0, { 
    // When a lambda has parameters, they go first, followed by '->'
    acc: Int, i: Int -> 
    print("acc = $acc, i = $i, ") 
    val result = acc + i
    println("result = $result")
    // The last expression in a lambda is considered the return value:
    result
})
```


### Generics 
```kt
fun main() {

    //  Custom Lambda Generic Function
    val lambdaList3 = listOf("Durium", "Mango", "Banana")
    val count3 = lambdaList3.customLamGenericsFunction { value ->
//      in lambda function last line get automatic return, so no need to mention return
        value.length >= 4
    }
    println("Custom-Lambda Generic function return value is $count3")

    // apply generics without lambda
    val count4 = lambdaList3.customLamGenericsFunction(fun (value): Boolean {
        return value.length >= 4
    })
    val count5 = lambdaList3.customLamGenericsFunction(fun (value): Boolean = value.length >= 4)
    println("Custom-Lambda Generic function return value is $count4")
    println("Custom-Lambda Generic function return value is $count5")

}

fun <T> List<T>.customLamGenericsFunction(value: (T) -> Boolean): Int {
    var counter = 0
    for (item in this) {
        if(value(item)) {
            counter++
        }
    }
    return counter
}
```

## Data Class
> data class ClassName( val somedata: String, val isChecked: Boolean: false )


Next: Kotlin OOP : [OOP Kotlin](oop-tour-kotlin.md) and [Advanced Kotlin](kotlin-advanced-lanuge.md)


### Lambda without braces for member references:
":\:" creates a member reference or a class reference.
```kt
fun main() {
    fun memberFn() = println("x")
    val lambdaFn: () -> Unit = ::memberFn // refers to memberFn(), thats why no braces for lambda
    lambdaFn() // print "x"
}

// Docs Example
    val stringPlus: (String, String) -> String = String::plus // referes to "plus" member function of the String Class
    println(stringPlus("Hello, ", "world!")) // prints "Hello World"
```

### with() Statement:
```kt
// Signature
with (instanceName) {
    // all operations to do with instanceName
}
```
Usages:

```kt
fun main() {
    val squareCabin = SquareCabin(6)

    println("\nSquare Cabin\n============")
    println("Capacity: ${squareCabin.capacity}")
    println("Material: ${squareCabin.buildingMaterial}")
    println("Has room? ${squareCabin.hasRoom()}")
}

// can rewrite this using "with(object/instance)" statement to apply context of that object
fun main(){
    val squareCabin = SquareCabin(7)
    with(squareCabin) {
    println("\nSquare Cabin\n============")
    println("Capacity: ${capacity}")
    println("Material: ${buildingMaterial}")
    println("Has room? ${hasRoom()}")
}
}
```