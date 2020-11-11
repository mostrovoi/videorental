package com.acme.videorental.infrastructure.configuration

import com.acme.videorental.domain.model.customer.CustomerRepository
import com.acme.videorental.domain.model.film.FilmRepository
import com.acme.videorental.domain.model.filmtransaction.FilmTransactionRepository
import com.acme.videorental.usecase.filmtransaction.FilmRentalAssembler
import com.acme.videorental.usecase.filmtransaction.FilmReturnAssembler
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

