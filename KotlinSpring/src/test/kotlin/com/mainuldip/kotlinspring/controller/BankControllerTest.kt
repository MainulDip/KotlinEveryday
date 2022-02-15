package com.mainuldip.kotlinspring.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
// @SpringBootTest will add this as been and setup text for entire application context. It is expensive as it will call the whole application context
@AutoConfigureMockMvc
internal class BankControllerTest {

    @Autowired
//    @Autowired to tell spring boot to initialize and provide us the been of this object when testing. It's a way of spring boots dependency injection
    lateinit var mockMvc: MockMvc


    @Test
    fun `should return all banks` () {
// act/when and assert/then together
//        mockMvc.get("/api/banks")
//            .andDo { print() }
//            .andExpect { status { isOk() } }

    }
}