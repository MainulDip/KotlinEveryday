package datastorageserialization

import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.name

fun main() {

    /**
     * Creating New Directory if not exists
     */
    val dir = "TextFiles"
    val directoryCreatedIfNotExists: Path = if (!Files.exists(Path(dir))) Files.createDirectory(Path(dir)) else Path(dir)
    println(directoryCreatedIfNotExists)

    /**
    * Creating, writing to a text file
    */
    val theTextFile = File(dir,"somefile.txt")
    val writeData = theTextFile.writeText("SomeText Data")

    /**
     * Updating/Appending To The Text File
     */
    val appendText = theTextFile.appendText("\nAppending Text With New Line \nThis line will be removed Next")

    /**
     * Reading Text File
     */
    val fileTextContent = theTextFile.readText(Charsets.UTF_8)
    println("\n-------Reading File---------\n\n$fileTextContent\n\n")

    /**
     * Updating/Delete Last Line From The Text File
     */
    // get the last line
    val getLinesList = theTextFile.readLines()
    val filteredText = getLinesList.filter { it != getLinesList.last() }
    // println(filteredText)
    // make text file blank
    theTextFile.writeText("")
    filteredText.forEach {
        theTextFile.appendText("$it\n")
    }

    println("------------ After Update : -----------\n\n${theTextFile.readText()}")


}