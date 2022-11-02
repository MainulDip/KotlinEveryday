package datastorageserialization

import protobufgenerated.Addressbook.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.*
import kotlin.system.exitProcess


// This function fills in a Person message based on user input.
fun promptPerson(): Person {
    print("Enter person ID: ")
    var id = readLine()?.toInt()

    print("Enter name: ")
    val name = readLine()

    print("Enter email address (blank for none): ")
    val email = readLine()
//    if (email != null) {
//        if (email.isNotEmpty()) {
//            this.email = email
//        }
//    }

    while (true) {
        print("Enter a phone number (or leave blank to finish): ")
        val number  = readLine()
        if (number?.isEmpty() == true) break

        print("Is this a mobile, home, or work phone? ")
        val type = when (readLine()) {
            "mobile" -> Person.PhoneType.MOBILE
            "home" -> Person.PhoneType.HOME
            "work" -> Person.PhoneType.WORK
            else -> {
                println("Unknown phone type.  Using home.")
                Person.PhoneType.HOME
            }
        }
//        var phones = phoneNumber {
//            this.number = number
//            this.type = type
//        }
    }

    val person = Person.newBuilder()
    person.name = name
    if (id != null) { person.id = id }
    person.email = email

    return person.build()

}

// Reads the entire address book from a file, adds one person based
// on user input, then writes it back out to the same file.
fun main() {
    // Create directory andy file if not exists
    val filename = "addressbookdata.txt"
    var dirname = "resources"

    val directoryCreatedIfNotExists: Path = if (!Files.exists(Path(dirname))) Files.createDirectory(Path(dirname)) else Path(dirname)
    val addressbookdatafile = File(dirname,filename).writeText("")

    val getPerson = promptPerson()
    val path = Path("$dirname/$filename")

    val initialAddressBook = path.inputStream().use {
        AddressBook.newBuilder().mergeFrom(it).build()
    }

//    val initialAddressBook = if (!path.exists()) {
//        println("File not found. Creating new file.")
//        addressBook {}
//    } else {
//        path.inputStream().use {
//            AddressBook.newBuilder().mergeFrom(it).build()
//        }
//    }
    path.outputStream().use {
        initialAddressBook.writeTo(it)
    }

    println(path.readText())
}

//fun main() {
//    println("Bismillah")
//    val person = Person.newBuilder()
//    person.setName("Hello")
//    println(person.name)
//    val addressbook = AddressBook.newBuilder()
//    addressbook.addPeople(person)
//    println(addressbook.toString())
//}