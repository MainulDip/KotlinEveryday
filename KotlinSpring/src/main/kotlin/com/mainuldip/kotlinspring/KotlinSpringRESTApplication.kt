package com.mainuldip.kotlinspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinSpringRESTApplication

fun main(args: Array<String>) {
	runApplication<KotlinSpringRESTApplication>(*args)
}
