package mapdatastructure

fun main() {
    println(dUserList.arrange<String>())
}

data class DUser(val name: String, val age: Int, val group: String)

var dUserList: List<DUser> = mutableListOf(DUser("First User", 21, "a"), DUser("Second User", 22, "b"),DUser("Third User", 23, "c"), DUser("First User", 21, "b"))

fun <T> List<DUser>.arrange(): Map<T, List<DUser>> =
    groupBy {
        it.name as T
    }

// {First User=[DUser(name=First User, age=21, group=a), DUser(name=First User, age=21, group=b)], Second User=[DUser(name=Second User, age=22, group=b)], Third User=[DUser(name=Third User, age=23, group=c)]}