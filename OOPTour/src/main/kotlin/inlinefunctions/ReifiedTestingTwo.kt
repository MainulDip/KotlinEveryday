package inlinefunctions


sealed class Mammal(val name: String)

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
// here "<reified T : Mammal>" is only necessary for the "list.filterIsInstance<T>()...." block to filter based on provided type
inline fun <reified T : Mammal> printAnimalResultFiltered(
    list: List<Mammal>,
    factCheck: Mammal.() -> Int
): List<Mammal> {
    if (list.isNotEmpty()) {
        list.filterIsInstance<T>() // To filter based on supplied type, we need to apply Generics T with <reified T : Mammal>
            .forEach {
                println("${it.javaClass.name} - ${it.factCheck()}")
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
    printAnimalResultFiltered<Panda>(crewCrewCrew, Mammal::knownSpeciesCount)
    println()
    printAnimalResultFiltered<Sloth>(crewCrewCrew, Mammal::knownSpeciesCount)
}