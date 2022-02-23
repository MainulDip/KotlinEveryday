package com.mainuldip.kotlinspring.controller

import com.mainuldip.kotlinspring.datasource.mock.MockBankDataSource
import com.mainuldip.kotlinspring.model.Bank
import com.mainuldip.kotlinspring.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
// @RestController marks it as been in the application context.
@RequestMapping("/api/banks")
//@RequestMapping()
class BankController ( private val service: BankService) {

//    @GetMapping("second")
//    fun banks(): MockBankDataSource {
//        val banks: MockBankDataSource = MockBankDataSource()
//        return banks
//    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFoundErrors(e: NoSuchElementException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(CloneNotSupportedException::class)
    fun handleCloneErrors(e: CloneNotSupportedException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @GetMapping()
    //function shorter syntax, if it has one liner return only, no need to put return keyword
    fun second(): Collection<Bank> = service.getBanks()

    @GetMapping("/{accountNumber}")
    fun getBank(@PathVariable accountNumber: String) : Bank {
//        "You're wanted with the account $accountNumber"
        return service.getBank(accountNumber)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBank(@RequestBody bank: Bank): Bank {
        return service.addBank(bank)
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    fun patchBank(@RequestBody bank: Bank): Bank = service.patchBank(bank)

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    fun deleteTheBank(@RequestBody id: String): String = service.deleteBank(id)
}
