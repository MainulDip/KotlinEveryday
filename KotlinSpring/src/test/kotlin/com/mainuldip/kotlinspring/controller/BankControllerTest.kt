package com.mainuldip.kotlinspring.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mainuldip.kotlinspring.model.Bank
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.Lifecycle
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
// @SpringBootTest uses for integration testing and this annotation will add this as been and setup text for entire application context. It is expensive as it will call the whole application context
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
        ) {

//    @Autowired
//    @Autowired to tell spring boot to initialize and provide us the been of this object when testing. It's a way of spring boots dependency injection
//    lateinit var mockMvc: MockMvc

//    @Autowired
//    lateinit var objectMapper: ObjectMapper


    val baseUrl = "/api/banks"




    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {

        @Test
        fun `should return all banks` () {
// act/when and assert/then together
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") { value("1234")}
                }

        }
    }




    @Nested
    @DisplayName("GET /api/bank")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should return the bank information associated with id` () {

            // arrange/given
            val accountNumber = 1234

            // act/when && assert/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber") { value("1234")}
                    jsonPath("$.trust") { value(1)}
                    jsonPath("$.transactionFee") { value(3)}
                }
        }

        @Test
        fun `should return Not Found if the account number does not exists` () {

        // arrange/given
            val accountNumber = "does not exists"

        // act/when && assert/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }

    @Nested
    @DisplayName("POST /api/bank")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {
        @Test
        fun `should be adding new bank account` () {

        // arrange/given
            val newBank = Bank("1234567", 12.00, 7)

        // act/when
            val perfoemPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            // assert/then
            perfoemPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                }
        }
    }

}