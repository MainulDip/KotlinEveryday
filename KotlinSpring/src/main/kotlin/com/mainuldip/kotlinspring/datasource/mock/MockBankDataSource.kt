package com.mainuldip.kotlinspring.datasource.mock

import com.mainuldip.kotlinspring.datasource.BankDataSource
import com.mainuldip.kotlinspring.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource: BankDataSource {
    val banks = mutableListOf<Bank>(Bank("1234", 1.0, 3),
                       Bank("2435", 2.0, 2),
                       Bank("3456", 3.0, 1))
    override fun getBanks(): Collection<Bank> = banks
    override fun retrieveBanks(): Collection<Bank> = banks
    override fun retrieveBank(id: String): Bank {
        return banks.firstOrNull() { it.accountNumber == id } ?: throw NoSuchElementException("Could not found bank account number which id is $id")
    }

    override fun createBank(bank: Bank): Bank {
        val idAlreadyExists = banks.filter { it.accountNumber == bank.accountNumber }
        if (idAlreadyExists.isEmpty()) banks.add(bank) else throw CloneNotSupportedException("Could not found bank account number which id is $bank")
        return bank
    }

    override fun patchBank(bank: Bank): Bank {
        val requestedBank = banks.firstOrNull() { it.accountNumber == bank.accountNumber }
        if (requestedBank != null){
//            banks = banks.filter { it.accountNumber != bank.accountNumber } as MutableList<Bank>
            banks.remove(requestedBank)
            banks.add(bank)
        } else {
            throw CloneNotSupportedException("Could not found bank account number which id is $bank")
        }
        return bank
    }

    override fun deleteBank(id: String): String {
        println(id)
        return id
    }

}