package org.jd.demo.kotlin.spring

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * @Auther jd
 */
@SpringBootApplication
class KotlinSpringApplication

fun main(args: Array<String>) {
        SpringApplication.run(KotlinSpringApplication::class.java, *args)
//    runApplication<KotlinSpringApplication>(*args)
}
