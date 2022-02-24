package com.mainuldip.kotlinspring.datasource.network

import com.mainuldip.kotlinspring.datasource.BankDataSource
import com.mainuldip.kotlinspring.model.Bank

class NetworkDataSource: BankDataSource {
    override fun getBanks(): Collection<Bank> {
        TODO("Not yet implemented")
    }

    override fun retrieveBanks(): Collection<Bank> {
        TODO("Not yet implemented")
    }

    override fun retrieveBank(id: String): Bank {
        TODO("Not yet implemented")
    }

    override fun createBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun patchBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun deleteBank(id: String): String {
        TODO("Not yet implemented")
    }
}