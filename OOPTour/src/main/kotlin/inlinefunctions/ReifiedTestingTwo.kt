package inlinefunctions

import java.util.*

sealed class Mammal(val name: String) {
    open fun relief() {}
}

data class Sloth(
    val slothName: String,
    val isTwoFingered: Boolean,
    var slothWeight: Int
) : Mammal(slothName)

data class Manatee(val manateeName: String) : Mammal(manateeName)

data class Panda(val pandaName: String) : Mammal(pandaName)

fun Mammal.knownSpeciesCount(): Int {
    return when (this) {
        is Sloth -> 6
        is Panda -> 2
        is Manatee -> 3
    }
}

// reified
inline fun <reified T : Mammal> printAnimalResultFiltered(
    list: List<Mammal>,
    factCheck: Mammal.() -> Int
): List<Mammal> {
    if (list.isNotEmpty()) {
        list.filterIsInstance<T>()
            .forEach {
                println("${it.javaClass.name} - ${it.factCheck()}")
            }
    }
    return list
}

inline fun <T> printAnimalResultFilteredSecond(
    list: List<T>,
    factCheck: T.() -> Int
): List<T> {
    if (list.isNotEmpty()) {
        list
            .forEach {
//                println("${it.name} - ${it.javaClass.name} - ${it.factCheck()}")
                println("$it")
            }
    }
    return list
}

fun main() {
    val crewCrewCrew = listOf(
        Sloth("Jerry", false, 15),
        Panda("Tegan"),
        Manatee("Manny")
    )

    println("\nSpecies count with list as param:")
//    printAnimalResultFiltered<Sloth>(crewCrewCrew, Mammal::knownSpeciesCount)
    printAnimalResultFiltered<Sloth>(crewCrewCrew, Mammal::knownSpeciesCount)
    println()

    printAnimalResultFilteredSecond<Mammal>(crewCrewCrew, Mammal::knownSpeciesCount)
}