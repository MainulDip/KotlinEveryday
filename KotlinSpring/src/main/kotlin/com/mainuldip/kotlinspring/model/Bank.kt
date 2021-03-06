package com.mainuldip.kotlinspring.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Bank (
    @JsonProperty("country")
//    @JsonProperty("name")
    val accountNumber: String,

    @JsonProperty("exchange_rate")
//    @JsonProperty("id")
    var trust: Double,

    @JsonProperty("record_fiscal_quarter")
//    @JsonProperty("id")
    val transactionFee: Int
    ){
    init {
        trust = trust.toDouble()
        println("Initializing Bank")
    }
}

//class Bank {
//    private val accountNumber: String
//    private val trust: Double
//    private val transactionFee: Int
//
//    constructor(accountNumber: String, trust: Double, transactionFee: Int){
//        this.accountNumber = accountNumber
//        this.trust = trust
//        this.transactionFee = transactionFee
//    }
//
////    fun getAccountNumber(): String = accountNumber
////
////    fun setAccountNumber( accountNumber: String ): Unit {
////        this.accountNumber = accountNumber
////    }
//
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as Bank
//
//        if (accountNumber != other.accountNumber) return false
//        if (trust != other.trust) return false
//        if (transactionFee != other.transactionFee) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        var result = accountNumber.hashCode()
//        result = 31 * result + trust.hashCode()
//        result = 31 * result + transactionFee
//        return result
//    }
//
//    override fun toString(): String {
//        return "Bank(accountNumber='$accountNumber', trust=$trust, transactionFee=$transactionFee)"
//    }
//
//
//}