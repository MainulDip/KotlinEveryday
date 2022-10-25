package dataSealedEnumClasses

fun main(args: Array<String>) {
    println(Resource.Success("Data"))
    println(Resource.Error(message = "Error Message"))

    println("-----------------Person Class-------------------------")

    val person = Person("Hello", Person.Gender.Male)
    val person2 = Person("World", Person.Gender.Female)

    println(person2.gender)
    println(person.name)

    val getGender: String = when(person.gender){
        Person.Gender.Male -> "Male"
        Person.Gender.Female -> "Female"
    }.exhaustive

    println("Person 1's gender is $getGender")

    /**
     * When calling as expression (not as statement)
     * a coed block is usually counted as an expression when it returns something
     * if used as expression, we don't need the exhaustive extension function for IDE support to cover every possible cases */
    val getGenderExpression = genderExpression(person.gender)
    println("getGenderExpression : $getGenderExpression")
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

/**
 * IDE Support
 * this help to get IDE support to cover all sealed classes' possible cases using "add remaining branches"
 */

val <T> T.exhaustive : T
    get() = this

/**
 * using when block with sealed class as an expression
 * use "add remaining branches" form IDE suggestion to generate all the possible cases */

fun genderExpression (gender: Person.Gender): String = when(gender) {
    Person.Gender.Female -> "Male"
    Person.Gender.Male -> "Female"
}