package com.acme.videorental.sharedKernel.infrastructure.configuration

import com.acme.videorental.customers.domain.CustomerRepository
import com.acme.videorental.purchases.domain.FilmRepository
import com.acme.videorental.rentals.application.FilmRentalAssembler
import com.acme.videorental.rentals.application.FilmReturnAssembler
import com.acme.videorental.rentals.domain.RentalRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AssemblerConfiguration {

    @Bean
    fun filmRentalAssembler(filmRepository: FilmRepository, rentalRepository: RentalRepository, customerRepository: CustomerRepository): FilmRentalAssembler {
        return FilmRentalAssembler(filmRepository = filmRepository, rentalRepository = rentalRepository, customerRepository = customerRepository)
    }

    @Bean
    fun filmReturnAssembler(filmRepository: FilmRepository, rentalRepository: RentalRepository, customerRepository: CustomerRepository): FilmReturnAssembler {
        return FilmReturnAssembler(filmRepository = filmRepository, rentalRepository = rentalRepository, customerRepository = customerRepository)
    }
}

