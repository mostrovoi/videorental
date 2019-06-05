package com.casumo.videorental.infrastructure.configuration

import com.casumo.videorental.domain.model.customer.CustomerRepository
import com.casumo.videorental.domain.model.film.FilmRepository
import com.casumo.videorental.domain.model.filmtransaction.FilmTransactionRepository
import com.casumo.videorental.usecase.filmtransaction.FilmRentalAssembler
import com.casumo.videorental.usecase.filmtransaction.FilmReturnAssembler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AssemblerConfiguration {

    @Bean
    fun filmRentalAssembler(filmRepository: FilmRepository, filmTransactionRepository: FilmTransactionRepository, customerRepository: CustomerRepository): FilmRentalAssembler {
        return FilmRentalAssembler(filmRepository = filmRepository, filmTransactionRepository = filmTransactionRepository, customerRepository = customerRepository)
    }

    @Bean
    fun filmReturnAssembler(filmRepository: FilmRepository, filmTransactionRepository: FilmTransactionRepository, customerRepository: CustomerRepository): FilmReturnAssembler {
        return FilmReturnAssembler(filmRepository = filmRepository, filmTransactionRepository = filmTransactionRepository, customerRepository = customerRepository)
    }
}

