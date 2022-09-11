package destructuring

// Destructuring using data class is simple because it provides componentN function by default

// destructuring works with data class, at it cas "operator fun componentN" built in.
// To apply destructuring with regular class, have to override componentN operator function
data class DUser(val name: String, val age: Int, val group: String)

var dUserList: List<DUser> = mutableListOf(DUser("First User", 21, "a"), DUser("Second User", 22, "b"),DUser("Third User", 23, "c"), DUser("First User", 21, "b"))

fun List<DUser>.arrange(): List<DUser> =
    // groupBy returns Map<Key, List<DUser>>, that's why on map, $it de-structured to (key, list)
    groupBy {
        it.name
    }.map { (key, list) -> DUser(key, list[0].age, list[0].group) }

fun main() {
    val users = dUserList.arrange()
    println("After Arranging using groupBy \nUsers : $users \nAnd DUser Count is : ${users.size} where original dUserList was ${dUserList.size}")
}

//After Arranging using groupBy
//Users : [DUser(name=First User, age=21, group=a), DUser(name=Second User, age=22, group=b), DUser(name=Third User, age=23, group=c)]
//And DUser Count is : 3 where original dUserList was 4