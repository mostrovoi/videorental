package com.casumo.videorental.infrastructure.configuration

import com.casumo.videorental.infrastructure.controller.customer.adapter.CustomerMapper
import com.casumo.videorental.infrastructure.controller.film.adapter.FilmMapper
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