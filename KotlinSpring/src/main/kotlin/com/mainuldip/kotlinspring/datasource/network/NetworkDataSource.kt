package com.mainuldip.kotlinspring.datasource.network

import com.mainuldip.kotlinspring.datasource.BankDataSource
import com.mainuldip.kotlinspring.datasource.network.dto.BankList
import com.mainuldip.kotlinspring.datasource.network.dto.UsersList
import com.mainuldip.kotlinspring.model.Bank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import org.springframework.web.client.getForObject
import java.io.IOException

@Repository("network")
class NetworkDataSource (@Autowired private val restTemplate: RestTemplate): BankDataSource {
    override fun getBanks(): Collection<Bank> {
        val response: ResponseEntity<BankList> =  restTemplate.getForEntity("https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od/rates_of_exchange")
        println(response.body?.data)
        return response.body?.data
            ?: throw IOException("Could not fetch banks from the network")
//        54.193.311.59
    }

    override fun retrieveBanks(): Collection<Bank> {
        val response: ResponseEntity<BankList> =  restTemplate.getForEntity("https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od/rates_of_exchange")
//        https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od/rates_of_exchange
        val data: Collection<Bank>? = response.body?.data

        val responseSecond: Collection<Any> =  restTemplate.getForObject("https://jsonplaceholder.typicode.com/users")

        println(responseSecond.size)
        return data
            ?: throw IOException("Could not fetch banks from the network")
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