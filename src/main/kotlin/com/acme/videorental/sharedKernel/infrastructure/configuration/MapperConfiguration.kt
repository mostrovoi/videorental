package com.acme.videorental.sharedKernel.infrastructure.configuration

import com.acme.videorental.customers.infrastructure.controller.adapter.CustomerMapper
import com.acme.videorental.purchases.infrastructure.controller.adapter.FilmMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MapperConfiguration {

    @Bean
    fun filmMapper(): FilmMapper {
        return FilmMapper()
    }

    @Bean
    fun customerMapper(): CustomerMapper {
        return CustomerMapper()
    }
}