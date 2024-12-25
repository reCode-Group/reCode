package com.dev.reCode

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication
@EnableScheduling
class ReCodeApplication

fun main(args: Array<String>) {
    runApplication<ReCodeApplication>(*args)
}

