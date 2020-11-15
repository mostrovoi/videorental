package com.acme.videorental

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = arrayOf("com.acme.videorental.sharedKernel.infrastructure.configuration",
        "com.acme.videorental.customers.infrastructure.controller",
        "com.acme.videorental.payments.infrastructure.controller",
        "com.acme.videorental.purchases.infrastructure.controller",
        "com.acme.videorental.rentals.infrastructure.controller",
        "com.acme.videorental.sharedKernel.infrastructure.exception"))
class VideorentalApplication

fun main(args: Array<String>) {
    runApplication<VideorentalApplication>(*args)
}

