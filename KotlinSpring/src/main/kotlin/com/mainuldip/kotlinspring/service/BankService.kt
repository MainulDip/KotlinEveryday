package com.mainuldip.kotlinspring.service

import com.mainuldip.kotlinspring.datasource.BankDataSource
import com.mainuldip.kotlinspring.model.Bank
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
// @Service annotation tells spring to make this been/class/object-of-it (BankService) available at runtime in the application context, and cam be injected via dependency injection into other objects and classes.
// Also, @Service annotation other developer that, this is a service level been, it is responsible for calling data source, handling exceptions, data transformation
// If the been/class is not @Service or @Repository, @Component is a generic annotation for making that been for component scanning in the application context.

// Note: We're calling the interface class and get the interface implementation through spring's @Repository annotation which will automatically create the benn of the class inside application context

class BankService (@Qualifier("network") private val dataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()
    fun getBank(id: String = ""): Bank {
        return dataSource.retrieveBank(id)
    }

    fun addBank(bank: Bank): Bank = dataSource.createBank(bank)
    fun patchBank(bank: Bank): Bank = dataSource.patchBank(bank)
    fun deleteBank(id: String): String = dataSource.deleteBank(id)
}