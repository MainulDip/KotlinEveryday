package scopefunctions

import kotlin.random.*
fun main() {

    // When called on an object with a predicate provided
    // If matched with the predicate, takeIf will return the object but takeUnless will return null
    // If predicate don't match, takeIf will return Null but takeUnless will return the Object

    val number = Random.nextInt(100)
    println("The number is : $number")

    // takeIf returns this object if it matches the predicate. Otherwise, it returns null. So, takeIf is a filtering function for a single object.
    val evenOrNull = number.takeIf { it % 2 == 0 }

    // takeUnless returns the object if it doesn't match the predicate and null if it does. The object is available as a lambda argument (it).
    val oddOrNull = number.takeUnless { it % 2 == 0 }


    println("even: $evenOrNull, odd: $oddOrNull")

}