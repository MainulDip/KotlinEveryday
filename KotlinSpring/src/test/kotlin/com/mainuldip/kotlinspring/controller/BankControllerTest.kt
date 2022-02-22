package com.mainuldip.kotlinspring.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mainuldip.kotlinspring.model.Bank
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

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
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newBank))
                    }
//                    jsonPath("$.accountNumber") { value("1234567")}
//                    jsonPath("$.trust") { value(12.00)}
//                    jsonPath("$.transactionFee") { value(7)}
                }
        }

        @Test
        fun `should return BAD REQUEST` () {

        // arrange/given
            val invalidBank = Bank("3456", 3.0, 1)

        // act/when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

        // assert/then
            performPost
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
        }
    }

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBank {

        @Test
        fun `should update an existing bank` () {

        // arrange/given
            val updateBank = Bank("1234", 3.0, 1)

        // act/when
            val performPatch = mockMvc.patch(urlTemplate = baseUrl, null, dsl = { ->
                // as there is a vararg parameter in the middle, the last lambda block needs to be called by named argument if not called outside the parenthesis
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updateBank)
            })

        // assert/then
            performPatch
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updateBank))
                    }
                }

            mockMvc.get("$baseUrl/${updateBank.accountNumber}")
                .andExpect {
                    status { isOk() }
                    content { json(objectMapper.writeValueAsString(updateBank)) }
                }
        } // @Test functionName Ends

        @Test
        fun `should return BAD REQUEST if no bank with given account number for updating patching ` () {

            // arrange/given
            val updateBankWrongInfo = Bank("1234567890", 3.0, 1)

            // act/when
            val performPatchWrongCheck = mockMvc.patch(urlTemplate = baseUrl, null, dsl = { ->
                // as there is a vararg parameter in the middle, the last lambda block needs to be called by named argument if not called outside the parenthesis
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updateBankWrongInfo)
            })

            // assert/then
            performPatchWrongCheck
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }



        } // @Test functionName Ends
    } // Nested class ClassName Ends

}