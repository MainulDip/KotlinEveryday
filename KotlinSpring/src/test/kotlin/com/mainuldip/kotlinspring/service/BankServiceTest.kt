package com.mainuldip.kotlinspring.service

import com.mainuldip.kotlinspring.datasource.BankDataSource
import com.mainuldip.kotlinspring.datasource.mock.MockBankDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BankServiceTest {


    private val dataSource: BankDataSource = mockk(relaxed = true)
    private val bankService = BankService(dataSource)

    @Test
    fun `should call its data source to retrieve banks` () {


    // arrange/given
//        every { dataSource.retrieveBanks() } returns emptyList()

    // act/when
        val banks = bankService.getBanks()


    // assert/then
        verify(exactly = 1) { dataSource.retrieveBanks() }



    }
}