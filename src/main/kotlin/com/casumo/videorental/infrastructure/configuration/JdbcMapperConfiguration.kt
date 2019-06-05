package com.casumo.videorental.infrastructure.configuration

import com.casumo.videorental.infrastructure.repository.customer.JdbcCustomerMapper
import com.casumo.videorental.infrastructure.repository.film.JdbcFilmMapper
import com.casumo.videorental.infrastructure.repository.filmtransaction.JdbcFilmTransactionMapper
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
    fun jdbcFilmTransactionMapper(): JdbcFilmTransactionMapper {
        return JdbcFilmTransactionMapper()
    }
}