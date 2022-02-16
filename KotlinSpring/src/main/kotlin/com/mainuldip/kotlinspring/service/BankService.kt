package com.mainuldip.kotlinspring.service

import com.mainuldip.kotlinspring.datasource.BankDataSource
import com.mainuldip.kotlinspring.model.Bank
import org.springframework.stereotype.Service

@Service
// @Service annotation tells spring to make this been/class/object-of-it (BankService) available at runtime in the application context, and cam be injected via dependency injection into other objects and classess.
// Also, @Service annotation other developer that, this is a service level been, it is responsible for calling data source, handling exceptions, data transformation
// If the been/class is not @Service or @Repository, @Component is a generic annotation for making that been for component scanning in the application context.
class BankService (private val dataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()
    fun getBank(id: String = ""): Bank {
        return dataSource.retrieveBank(id)
    }
}