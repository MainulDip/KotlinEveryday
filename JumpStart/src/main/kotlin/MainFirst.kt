//fun main(args: Array<String>) {
//    println("Hello World!")
//
//    // Try adding program arguments via Run/Debug configuration.
//    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
//    println("Program arguments: ${args.joinToString()}")
//}

fun main() {
    println("Hello World")

//    Note: var and val
    var x: Int = 3 // 3L will be 64bit (Long)

    println("This value is $x") // print with line break at the end
    x = 4
    println("But The Value is now $x")
    val y: Int = 7 // val is constant
    print("Hello again $y") // will not put line break at the end
    println("")

//    types: 3 | 3L | 3.00 (double) | 3.00f (float)

    val wholeNumber: Int = 3
    val bigNumber: Long = 3L
    val preciseDecimal: Double = 3.33 // takes more memory
    val decimal: Float = 3.33f
    val checkBoolean: Boolean = false
    val someString: String = "hello world"
    val singleLetter: Char  = 'a' //

//    Mathematical operations

    val m = 3 * 4
    println("The value of multiply (m) is $m")
    val d = 3/4 // rerun 0 as Integer, will not return float value
    println("The value of divide (d) is $d")
    val f = 3f / 4f // need to specify float identifier to get the float value
    println("The value of float divide (f) is $f")
    val r = 10 % 3 // modulus or reminder operator
    println("The value of r after modulus operation is $r")

//  Boolean Check
    val amIAdult = true
    val amIProgrammer = true
    val anIAdultProgrammer = amIAdult && amIProgrammer
    println("Boolean operation for amIAdultProgrammer = $anIAdultProgrammer")
//  Also || , == , != , !( val1 != val2 )

//  String and operations
    val string = "Use double quotes for string and single quote for Char (Single letter)"
    println(string.uppercase())

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

//  kotlin is null safety language
//    val nullValue: Int? = null
//    val number1 = readLine() ?: "0" // Assign default value
//    val number2 = readLine() ?: "0"
////    val inputResult = number1!!.toInt() + number2!!.toInt()
//    val inputResult = number1.toInt() + number2.toInt()
////    val userInput = readLine() // return String type
//    println("userInput result is $inputResult")

//  Lists
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

//  Loop
    var counter = 0
    while( counter < shoppingList.size) {
        println("immutable shoppingList on loop $counter is ${shoppingList[counter]}")
        counter++
    }

    for((index, item) in mutableShoppingList.withIndex() ) {
        println("mutable printing from for loop on shoppingList $index is $item")
    }

//    for ( number in 1..100){
//        println("Outputting range in for loop wher number = $number")
//    }

    val checkWhen = 4
    when(checkWhen) {
        in 1..2 -> println("checkWhen is between 1 and 2")
        in 3..10 -> println("checkWhen is between 3 and 10")
        in 11..20 -> println("checkWhen is between 11 and 20")
        else -> {
            println("checkWhen is not in the range above")
        }
    }

    printNumber()


    val evenNum = 7
    val checkEven = checkIsEven(evenNum)
    println("checkEven for $evenNum is ${if(checkEven) "even" else "\"odd\""}")

    println(defaultArgVal())
    val checkEven2 = checkIsEven(number = evenNum)

    val oddValue = 3
    println(oddValue.isOdds())

//    import ../../org.exampe.Animal
//    val dog = Animal(name = "Dog")
    val dog = Dog()
    dog.bark()
    val cat = Cat()
    cat.meao()
    cat.makeSound()
    dog.makeSound()

    //Anonymous class
    val bear = object : Animal(name = "Cow") {
        override fun makeSound() {
            println("Calling From Anonymous class and the nos is \"Humba\"")
        }
    }
    bear.makeSound()

//    val numberExceptionCheck = readLine() ?: "0"
//    val parsedNumber = try {
//        numberExceptionCheck.toInt()
//    } catch (e: Exception) {
//        7
//    }
//    println(parsedNumber)

//    Lambda Function
    val lambdaList = listOf("apple", "orange", "cherry")
    val count = lambdaList.count { value ->
//      in lambda function last line get automatic return, so no need to mention return
        value.length == 6
    }
    println("Lambda function return value is $count")

//  Custom Lambda Function
    val lambdaList2 = listOf("apple", "orange", "cherry")
    val count2 = lambdaList2.useWithLambda { value ->
//      in lambda function last line get automatic return, so no need to mention return
        value.length == 6
    }
    println("Custom-Lambda function return value is $count2")

    //  Custom Lambda Generic Function
    val lambdaList3 = listOf("Durium", "Mango", "Banana")
    val count3 = lambdaList2.customLamGenericsFunction { value ->
//      in lambda function last line get automatic return, so no need to mention return
        value.length >= 4
    }
    println("Custom-Lambda Generic function return value is $count3")


}

fun List<String>.useWithLambda(fn: (String) -> Boolean): Int {
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

fun <T> List<T>.customLamGenericsFunction(value: (T) -> Boolean): Int {
    var counter = 0
    for (item in this) {
        if(value(item)) {
            counter++
        }
    }
    return counter
}

fun printNumber(){
    for(i in 1..10){
        println("Calling function printNumber and it returns $i")
    }
}

fun checkIsEven(number: Int): Boolean{
    return number % 2 == 0
}

fun defaultArgVal(number: Int = 7): Boolean { return number % 2 == 0}

// extension function: extend an already existed type
// call extension function like object function
fun Int.isOdds(): Boolean {
    return this % 2 != 0
}
