package datastorageserialization

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path

/**
 * Do JSON Serialization, Store as Text File as data.json
 * append some more data and read back all data
 */


fun main() {
    val dataList : List<Data> = listOf(Data(42, "str"), Data(12, "test"))
    val sDataList: String = Json.encodeToString(dataList)

    // store data to a file inside TextFiles Directory
    val dir = "TextFiles"
    val directoryCreatedIfNotExists: Path = if (!Files.exists(Path(dir))) Files.createDirectory(Path(dir)) else Path(dir)

    val theTextFile = File(dir,"data.json")
    val writeData = theTextFile.writeText(sDataList)

    // Read Data From data.json, decode as data and iterate over the items
    val fileDataJson = theTextFile.readText()
    val dSerialDataList = Json.decodeFromString<List<Data>>(fileDataJson)
    dSerialDataList.forEach { data -> println("data.a = ${data.a} and data.b = ${data.b}")}

    // append some more data and update data.json file
    val dSerialDataListMutable = Json.decodeFromString<MutableList<Data>>(fileDataJson)
    dSerialDataListMutable.add(Data(76, "Good"))

    theTextFile.writeText(Json.encodeToString(dSerialDataListMutable))

    println("\n\n-----------After Updating File--------------\n\n${theTextFile.readText()}")



}