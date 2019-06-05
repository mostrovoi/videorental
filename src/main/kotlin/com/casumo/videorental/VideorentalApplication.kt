package com.casumo.videorental

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = arrayOf("com.casumo.videorental.infrastructure.configuration",
        "com.casumo.videorental.infrastructure.controller", "com.casumo.videorental.infrastructure.exception"))
class VideorentalApplication

fun main(args: Array<String>) {
    runApplication<VideorentalApplication>(*args)
}

