package dataSealedEnumClasses

fun main() {
    val b = Bank("777", 12.34, 7)
    println("Bank Data is \$b = $b")

}
data class Bank (
    val accountNumber: String,
    var trust: Double,
    val transactionFee: Int
){

    init {
        trust = trust.toDouble()
        println("Initializing Bank")
    }
}