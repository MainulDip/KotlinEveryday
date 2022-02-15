package com.mainuldip.kotlinspring.datasource.mock

import com.mainuldip.kotlinspring.datasource.BankDataSource
import com.mainuldip.kotlinspring.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource: BankDataSource {
    val banks = listOf(Bank("1234", 1.0, 3),
                       Bank("2435", 2.0, 2),
                       Bank("3456", 3.0, 1))
    override fun getBanks(): Collection<Bank> = banks
}