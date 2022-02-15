package com.mainuldip.kotlinspring.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.function.Consumer

internal class MockBankDataSourceTest {

    private val mockDataSource: MockBankDataSource = MockBankDataSource()

    @Test
    fun `should provide a collection of banks` () {

    // act/when
        val banks = mockDataSource.getBanks()

    // assert/then
        assertThat(banks).isNotEmpty
        assertThat(banks).size().isGreaterThanOrEqualTo(3)

    }

    @Test
    fun `should provide some mock data ` () {
    // act/when
        val banks = mockDataSource.getBanks()

    // assert/then
        assertThat(banks).allMatch{
            it.accountNumber.isNotEmpty() &&
            it.trust != 0.00 &&
            it.transactionFee != 0
        };

//        assertThat(banks).allSatisfy(Consumer {
//            it.accountNumber.isNotEmpty() &&
//                    it.trust != 0.00 &&
//                    it.transactionFee != 0
//        })


    }
}