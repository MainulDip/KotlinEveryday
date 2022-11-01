package datastorageserialization


import protobufgenerated.*
import protobufgenerated.Addressbook.AddressBook
import protobufgenerated.Addressbook.AddressBookOrBuilder
//import protobufgenerated.Addressbook.*
import protobufgenerated.Something


// This function fills in a Person message based on user input.
//fun promptPerson(): Person {
//    print("Enter person ID: ")
//    var id = readLine()?.toInt()
//
//    print("Enter name: ")
//    val name = readLine()
//
//    print("Enter email address (blank for none): ")
//    val email = readLine()
//    if (email != null) {
//        if (email.isNotEmpty()) {
//            this.email = email
//        }
//    }

//    while (true) {
//        print("Enter a phone number (or leave blank to finish): ")
//        val number  = readLine()
//        if (number?.isEmpty() == true) break
//
//        print("Is this a mobile, home, or work phone? ")
//        val type = when (readLine()) {
//            "mobile" -> Person.PhoneType.MOBILE
//            "home" -> Person.PhoneType.HOME
//            "work" -> Person.PhoneType.WORK
//            else -> {
//                println("Unknown phone type.  Using home.")
//                Person.PhoneType.HOME
//            }
//        }
//        phones += phoneNumber {
//            this.number = number
//            this.type = type
//        }
//    }
//}

// Reads the entire address book from a file, adds one person based
// on user input, then writes it back out to the same file.
//fun main(args: List) {
//    if (arguments.size != 1) {
//        println("Usage: add_person ADDRESS_BOOK_FILE")
//        exitProcess(-1)
//    }
//    val path = Path(arguments.single())
//    val initialAddressBook = if (!path.exists()) {
//        println("File not found. Creating new file.")
//        addressBook {}
//    } else {
//        path.inputStream().use {
//            AddressBook.newBuilder().mergeFrom(it).build()
//        }
//    }
//    path.outputStream().use {
//        initialAddressBook.copy { peopleList += promptPerson() }.writeTo(it)
//    }
//}

fun main() {
    println("Bismillah")
    val person = Addressbook.Person.newBuilder()
    person.setName("Hello")
    println(person.name)
    val addressbook = AddressBook.newBuilder()
    addressbook.addPeople(person)
    println(addressbook.toString())
}