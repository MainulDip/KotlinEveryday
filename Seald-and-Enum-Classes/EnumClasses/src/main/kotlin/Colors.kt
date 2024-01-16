package org.example

/**
 * as each enum constants is an instance of the Enum Itself,
 * the constructor of the Enum is passed down to those member enum constants. those constants cannot be changed once defined from outside
 * Note: the `constant`s are immutable
 * and the purpose of enum class is to define a collection of type-safe (safe to type) values
 */

enum class Color(var rgb: String) {
    RED("0xFF0000"),
    GREEN("0x00FF00"),
    BLUE("0x0000FF")
}


fun main() {
    println(Color.RED) // Print -> RED
    println(Color.RED.rgb) // Print -> 0xFF0000
    println(Color.valueOf("RED")) // Print -> RED
    println(Color.valueOf("RED").rgb) // Print -> 0xFF0000
    for (item in Color.values()){
        println(item.rgb)
    }
//  print   0xFF0000
//  print   0x00FF00
//  print   0x0000FF
}