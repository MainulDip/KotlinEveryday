import java.time.LocalDate

fun main() {
    val epoch = LocalDate.of(1970, 1, 3)

    if(epoch.isTuesday()) {
        println("This was Tuesday $epoch")
    } else {
        println("Nope, it was not Tuesday $epoch")
    }
}


fun LocalDate.isTuesday(): Boolean {
    return toEpochDay().toInt() == 2
}
