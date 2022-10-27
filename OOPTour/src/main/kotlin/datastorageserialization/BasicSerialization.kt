package datastorageserialization

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun main() {
    /*
    * Basic Serialization
    */
    val json = Json.encodeToString(Data(42, "str"))
    println(json) // {"a":42,"b":"str"}

    /*
    * Collection Serialization
    */
    val dataList = listOf(Data(42, "str"), Data(12, "test"))
    val jsonList = Json.encodeToString(dataList)
    println(jsonList) // [{"a":42,"b":"str"},{"a":12,"b":"test"}]

    /*
    * Deserialization
    */
    val dJson = Json.decodeFromString<Data>(json)
    println(dJson)
    println("Deserializing Json to the Data class property where a = ${dJson.a} and b = ${dJson.b}")
}

@Serializable
data class Data(val a: Int, val b: String)