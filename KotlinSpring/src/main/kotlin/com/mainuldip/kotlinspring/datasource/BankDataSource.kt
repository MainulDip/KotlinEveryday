package com.mainuldip.kotlinspring.datasource

import com.mainuldip.kotlinspring.model.Bank

interface BankDataSource {
    fun getBanks(): Collection<Bank>
    fun retrieveBanks(): Collection<Bank>
    fun retrieveBank(id: String): Bank
    fun createBank(bank: Bank): Bank
    fun patchBank(bank: Bank): Bank
    fun deleteBank(id: String): String
}