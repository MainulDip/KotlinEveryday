package com.mainuldip.kotlinspring.datasource

import com.mainuldip.kotlinspring.model.Bank

interface BankDataSource {
    fun getBanks(): Collection<Bank>
    fun retrieveBanks(): Collection<Bank>
}