## Kotlin Language Tour:
It's a quick language tour to re-connect with most of the Kotlin language syntaxes and features.

### Kotlin vs Swift lambda and functions return type:
- define phase, both swift and kotlin use `()-> returnType` as defining signature
- both use curly braces to surround lambda when applying
- kotlin use `->` and swift use `in` to separate lambda body from arguments
- both support trailing lambda
- don't confuse, swift use `->`, and kotlin use `:` for functions return.
- but when a function returning another function, kotlin use `()->T`. Swift use this for everything.
- kotlin use `()` for lambda's parameter destructuring in applying phase
- Swift/Java use `Void` and Kotlin use `Unit` for no return (side effect operations)
```swift
{ (<#parameters#>) -> <#return type#> in
   <#statements#>
}
```
```kotlin
{ <#parameters#> -> <#statements#> }
```

### Calling Java From Kotlin:
Associated Java Code Should Inside main/java/ directory (By default). This can be changed by adding sourceSets.main { java.srcDirs("src/main/myJava", "src/main/myKotlin") } into build.gradle file
Docs: https://kotlinlang.org/docs/gradle.html#kotlin-and-java-sources....

### Variables and Types:
```kotlin
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

### Expression (returns) vs Statement (assignments):
* Expression: In Kotlin, an expression may be used as a statement or used as an expression depending on the context. As all expressions are valid statements, standalone expressions may be used as single statements or inside code blocks. Expression usually returns result (at least Unit). https://kotlinlang.org/spec/statements.html

* Statement: As all expressions are valid statements. In java any line ends with semicolon is a statement. Statements usually don't returns, it assigns, like `val somethis = 77` | `class SomeClass{}`. 

So if a `when` block returns and the returned value is captured & assigned to a variable, as a whole, it's a `Statement`.

### `when` block:
When Signature
```kotlin
// when as `expression`
when (variable) {
    matches-value -> execute-this-code
    matches-value -> execute-this-code
    else -> if nothing match, execute this
    // if else block is not given, IDE will show "exhaustive" error (it must handle all the cases possible)
}
```
When Uses
```kotlin
fun main() {
    val myFirstDice = Dice(6)
    val rollResult = myFirstDice.roll()
    val luckyNumber = 4

    // when as `expression`
    when (rollResult) {
        luckyNumber -> println("You won!")
        1 -> println("So sorry! You rolled a 1. Try again!")
        2 -> println("Sadly, you rolled a 2. Try again!")
        3 -> println("Unfortunately, you rolled a 3. Try again!")
        5 -> println("Don't cry! You rolled a 5. Try again!")
        6 -> println("Apologies! You rolled a 6. Try again!")
    }

    // when can also return a value, here when as `statement` as assignments is going on as a whole.
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

### Single-line-expression function:
When the function body consists of a single expression, the curly braces can be replaced by `=`
```kotlin
fun double(x: Int): Int = x * 2
fun doubleO(x: Int) = x * 2 
```

### <a name="arithmetic-tasks"></a> Arithmetic Operators:
> Operators for mathematical operation like + | - | * | / | %
```kotlin
val m = 3 * 4
println("The value of multiply (m) is $m")
val d = 3/4 // rerun 0 as Integer, will not return float value
println("The value of divide (d) is $d")
val f = 3f / 4f // need to specify float identifier to get the float value
println("The value of float divide (f) is $f")
val r = 10 % 3 // modulus or reminder operator
println("The value of r after modulus operation is $r")
```

### <a name="check-boolean"></a> Multiple evaluation and Boolean
```kotlin
val amIAdult = true
val amIProgrammer = true
val anIAdultProgrammer = amIAdult && amIProgrammer
println("Boolean operation for amIAdultProgrammer = $anIAdultProgrammer")
//  Also || , == , != , !( val1 != val2 )
```

### <a name="string"></a> String and Operations
```kotlin
val string = "Use double quotes for string and single quote for Char (Single letter)"
println(string.uppercase())
```

### Conditionals | No Ternary if/else:
```kotlin
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
### <a name="default-value-null-safetly"></a> Default value and null safety
> ?: , ? , !!
```kotlin
//  kotlin is null safety language
    val nullValue: Int? = null
    val number1 = readLine() ?: "0" // Assign default value
    val number2 = readLine() ?: "0"
//    val inputResult = number1!!.toInt() + number2!!.toInt()
    val inputResult = number1.toInt() + number2.toInt()
//    val userInput = readLine() // return String type
    println("userInput result is $inputResult")
```

### <a name="list"></a> List
```kotlin
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

### <a name="loop"></a> Loop
> check: https://kotlinlang.org/docs/control-flow.html#for-loops
```kotlin
// while loop
    val shoppingList = listOf<String>("Hello", "World");
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
        println("Outputting range in for loop where number = $number")
    }
```

### <a name="mapfilter"></a> Map and Filter (Higher Order Functions):
```kotlin
fun main() {
    val numbers = mutableListOf("one", "two", "three", "four", "five")
    println(numbers.map{it.length}) // [3, 3, 5, 4, 4]
    println(numbers.map{it.length}.filter{it > 3}) // [5, 4, 4]
    println(numbers.filter{it != "five"}) // [one, two, three, four]
}
```

### <a name="def-callling-function"></a> Defining Calling Functions
```kotlin
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
val checkEven2 = checkIsEven(number = evenNum) // adds good readability
// Setting Function's Default Args Value.
fun defaultArgVal(number: Int = 7): Boolean { return number % 2 == 0}
println(defaultArgVal())
```kotlin

### <a name="extension-function"></a> Extension Function
```kotlin
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
```kotlin
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


### <a name="anonymous-class"></a> Anonymous Class
```kotlin
//Anonymous class
    val bear = object : Animal(name = "Cow") {
        override fun makeSound() {
            println("Calling From Anonymous class and the nos is \"Humba\"")
        }
    }
    bear.makeSound()
```

### <a name="exception-handling"></a> Exception Handling
```kotlin
val numberExceptionCheck = readLine() ?: "0"
val parsedNumber = try {
    numberExceptionCheck.toInt()
} catch (e: Exception) {
    7
}
println(parsedNumber)
```

### <a name="lambda-function"></a> Lambda Function
The main principal is to pass value/s to the lambda parameter/s and use as callback later utilizing the passed parameter/s....
 
Note: When calling there is no parentheses ()" before arrow notation "->" like defining. Also no return statement. return@funname is allowed. When calling "()" can be used as destructured syntax.
> Lamda: Functions that are passed as parameter/args of another function

```kotlin
fun main(){
    //  Custom Lambda Function
    val lambdaList2 = listOf("apple", "orange", "cherry")
    val count2 = lambdaList2.customLamFunction { value ->
//      in lambda function last line get automatic return, so no need to mention return
        value.length == 6
    }
    println("Custom-Lambda function return value is $count2")
    // No "return" statement is allowed. only return@func (return@customLambdaFunction in this case) is allowed.the last line of the lambda is the automatic return statement
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

### Destructuring:
https://subscription.packtpub.com/book/application-development/9781787123687/5/ch05lvl1sec63/destructuring-in-lambda-expressions.
```kotlin
data class User(val name: String, val surname: String, val phone: String) 
 
val (name, surname, phone) = user 

    val showUser: (User) -> Unit = { (name, surname, phone) -> 
        println("$name $surname have phone number: $phone")  
    } 

    val user = User("Marcin", "Moskala", "+48 123 456 789") 
    showUser(user) 
    // Marcin Moskala have phone number: +48 123 456 789 
```


### <a name="lambda-type-instantiation"></a> Lambda Type Declaration and Instantiation:
```kotlin
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


### Generics | `fun <T> name(x: T, y: T): T {...}`:
Define generics extension like `fun <T> List<T>.nameFn(value: ((T) -> Boolean)): Int {...}`
```kotlin
fun main() {

    //  Custom Lambda Generic Function
    val lambdaList3 = listOf("Durium", "Mango", "Banana")
    val count3 = lambdaList3.customLamGenericsFunction { value ->
//      in lambda function last line get automatic return, so no need to mention return
        value.length >= 4
    }
    println("Custom-Lambda Generic function return value is $count3")

    // apply generics without lambda
    // val count4 = lambdaList3.customLamGenericsFunction(fun (value: String): Boolean {  return value.length >= 4  })
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
### Data Class:
Signature `ClassName( val somedata: String, val isChecked: Boolean: false )`
Braces after the constructor can be omitted if empty (applies classes also) 
It required to declare var/val before constructor params. 

### <a name="lambda-without-braces-member-references"></a> Lambda without braces for member references:
"::" creates a member reference or a class reference
```kotlin
fun main() {
    fun memberFn() = println("x")
    val lambdaFn: () -> Unit = ::memberFn // refers to memberFn(), thats why no braces for lambda
    lambdaFn() // print "x"
}

//  Docs Examples
    val stringPlus: (String, String) -> String = String::plus // referes to "plus" member function of the String Class
    println(stringPlus("Hello, ", "world!")) // prints "Hello World"
```

### with(i) Statement:
```kotlin
// Signature
with (instanceName) {
    // all operations to do with instanceName
}
```
Usages:

```kotlin
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

### List groupBy:
```kotlin
fun main() {
    println(dUserList.arrange<String>())
}

data class DUser(val name: String, val age: Int, val group: String)

var dUserList: List<DUser> = mutableListOf(DUser("First User", 21, "a"), DUser("Second User", 22, "b"),DUser("Third User", 23, "c"), DUser("First User", 21, "b"))

fun <T> List<DUser>.arrange(): Map<T, List<DUser>> =
    groupBy {
        it.name as T
    }

// {First User=[DUser(name=First User, age=21, group=a), DUser(name=First User, age=21, group=b)], Second User=[DUser(name=Second User, age=22, group=b)], Third User=[DUser(name=Third User, age=23, group=c)]}
```

### Labeling returns `label@` & `return@label` | Local vs Non-Local Returns:

```kotlin
fun bar() {
    listOf(1, 2, 3, 4, 5).forEach sth@{ // sth@ is labled
        if (it == 3) {
            println("skip this and run rest"); 
            return@sth // local return to sth@ (foreach)
        }  
        println(it)
    }
    println("this point reachable because of labled return (local)")
}

fun foo() {
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) {
            println("skip the whole loop because of the non-local return underneth");
            return // non-local return directly to the caller of foo()
        }
        println(it)
    }
    println("this point is unreachable because of non-local return")
}


fun main() {
    println("Non-Local Return:")
    foo()
    println("\nLocal Return using lable@ (sth@):")
    bar()
}

// Non-Local Return:
// 1
// 2
// skip the whole loop because of the non-local return underneth

// Local Return using lable@ (sth@):
// 1
// 2
// skip this and run rest
// 4
```

### Iterable.flatten():
Returns a single list of all elements from all collections in the given collection.
```kotlin
public fun <T> Iterable<Iterable<T>>.flatten(): List<T>
```