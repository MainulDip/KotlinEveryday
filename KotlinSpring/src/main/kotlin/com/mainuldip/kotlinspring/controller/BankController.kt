package com.mainuldip.kotlinspring.controller

import com.mainuldip.kotlinspring.datasource.mock.MockBankDataSource
import com.mainuldip.kotlinspring.model.Bank
import com.mainuldip.kotlinspring.service.BankService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
// @RestController marks it as been in the application context.
@RequestMapping("/api/banks")
class BankController ( private val service: BankService) {

//    @GetMapping("second")
//    fun banks(): MockBankDataSource {
//        val banks: MockBankDataSource = MockBankDataSource()
//        return banks
//    }

    @GetMapping()
    //function shorter syntax, if it has one liner return only, no need to put return keyword
    fun second(): Collection<Bank> = service.getBanks()
}