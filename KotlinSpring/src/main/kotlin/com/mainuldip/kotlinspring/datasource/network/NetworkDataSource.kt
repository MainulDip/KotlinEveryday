package com.mainuldip.kotlinspring.datasource.network

import com.mainuldip.kotlinspring.datasource.BankDataSource
import com.mainuldip.kotlinspring.datasource.network.dto.BankList
import com.mainuldip.kotlinspring.model.Bank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.io.IOException

@Repository("network")
class NetworkDataSource (@Autowired private val restTemplate: RestTemplate): BankDataSource {
    override fun getBanks(): Collection<Bank> {
        val response: ResponseEntity<MutableCollection<Bank>> =  restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users")
        return response.body
            ?: throw IOException("Could not fetch banks from the network")
//        54.193.311.59
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