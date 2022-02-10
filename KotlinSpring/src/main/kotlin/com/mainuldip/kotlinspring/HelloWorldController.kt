package com.mainuldip.kotlinspring

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/hello")
class HelloWorldController {

    @GetMapping
    fun helloWorld(): String {
        return "Hello, this is a Rest endpoint"
    }

    @GetMapping("hello")
    //function shorter syntax, if it has one liner return only, no need to put return keyword
    fun helloWorld2(): String = "Hello from function expression body"
}