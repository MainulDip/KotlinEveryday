package datastorageserialization

import java.io.File
import java.net.URL
import java.nio.file.Files
import kotlin.io.path.Path

fun main() {
    /**
     * Creating New Directory if not exists
     */
    val dir = "TextFiles"
    val directoryCreatedIfNotExists = if (!Files.exists(Path(dir))) Files.createDirectory(Path(dir)) else dir
    println(directoryCreatedIfNotExists)
    /*
    * Creating, writing to a file
    */
    val writeData = File(File(dir),"somefile.txt").writeText("SomeText Data")

}