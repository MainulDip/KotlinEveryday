package dataSealedEnumClasses

fun main(args: Array<String>) {
    println(Resource.Success("Data"))
    println(Resource.Error(message = "Error Message"))
    val person = Person("Hello", Person.Gender.Male)
    val person2 = Person("World", Person.Gender.Female)

    println(person2.gender)
    println(person.name)

    val getGender: String = when(person.gender){
        is Person.Gender.Male -> "Male"
        is Person.Gender.Female -> "Female"
    }

    println("Person 1's gender is $getGender")
}

sealed class Resource<T> (data: T? = null, message: String? = null){
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: T) : Resource<T>(message)
}

data class Person(val name: String, val gender: Gender ) {
    sealed class Gender {
        object Male: Gender()
        object Female: Gender()
    }
}