package com.acme.videorental

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = arrayOf("com.acme.videorental.infrastructure.configuration",
        "com.acme.videorental.infrastructure.controller", "com.acme.videorental.infrastructure.exception"))
class VideorentalApplication

fun main(args: Array<String>) {
    runApplication<VideorentalApplication>(*args)
}

