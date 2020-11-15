package com.acme.videorental.sharedKernel.infrastructure.configuration

import com.acme.videorental.customers.infrastructure.JdbcCustomerMapper
import com.acme.videorental.purchases.infrastructure.JdbcFilmMapper
import com.acme.videorental.rentals.infrastructure.JdbcRentalMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JdbcMapperConfiguration {

    @Bean
    fun jdbcFilmMapper(): JdbcFilmMapper {
        return JdbcFilmMapper()
    }

    @Bean
    fun jdbcCustomerMapper(): JdbcCustomerMapper {
        return JdbcCustomerMapper()
    }

    @Bean
    fun jdbcRentalMapper(): JdbcRentalMapper {
        return JdbcRentalMapper()
    }
}